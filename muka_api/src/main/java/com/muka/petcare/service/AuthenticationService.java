package com.muka.petcare.service;

import com.muka.petcare.config.JwtUtils;
import com.muka.petcare.dto.request.auth.AuthenticationRequest;
import com.muka.petcare.dto.response.auth.AuthenticationResponse;
import com.muka.petcare.dto.response.auth.RefreshTokenRequest;
import com.muka.petcare.entity.DisabledToken;
import com.muka.petcare.entity.User;
import com.muka.petcare.exception.AppException;
import com.muka.petcare.exception.ErrorCode;
import com.muka.petcare.repository.DisabledTokenRepository;
import com.muka.petcare.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    //đăng nhập -> trả về token để truy cập
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        User user = null;

        if(request.getUserName().contains("@")){
            Optional<User> userOptional = userRepository.findByUserEmail(request.getUserName());
            if(userOptional.isPresent()) user = userOptional.get();
            else throw new AppException(ErrorCode.EMAIL_NAME_NOT_EXISTED);
        }
        else {
            Optional<User> userOptional = userRepository.findByUserName(request.getUserName());
            if(userOptional.isPresent()) user = userOptional.get();
            else throw new AppException(ErrorCode.USER_NAME_NOT_EXISTED);
        }

        boolean authenticated = passwordEncoder.matches(request.getUserPassword(), user.getUserPassword());
        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        //đăng nhập thành công -> tạo token
        var accessToken = jwtUtils.generateToken(user, true);
        var refreshToken = jwtUtils.generateToken(user, false);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //refresh token -> trả về token mới để truy cập
    public String refreshAccessToken(String accessToken, String refreshToken) throws ParseException, JOSEException {
        // kiểm tra tính hợp lệ của refreshToken
        if(!jwtUtils.checkTokenValidity(refreshToken, false)){
            throw new RuntimeException("Invalid or expired refresh token");
        }

        //kiểm tra refreshToken có trong DB
        JWTClaimsSet jwtClaimsSet = jwtUtils.decodeToken(refreshToken, false);
        if(jwtUtils.isTokenInBlackList(jwtClaimsSet.getJWTID())){
            throw new RuntimeException("Refresh token is blacklisted");
        }

        //lấy thông tin người dùng từ token
        String userName = jwtClaimsSet.getSubject();
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if(userOptional.isEmpty()){
            throw new AppException(ErrorCode.USER_NAME_NOT_EXISTED);
        }
        User user = userOptional.get();

        //tạo accessToken mới
        return jwtUtils.generateToken(user, false);
    }

    //đăng xuất
    public void logout(String accessToken, String refreshToken) throws ParseException, JOSEException {
        boolean isAccessTokenVallid = jwtUtils.checkTokenValidity(accessToken, true);
        boolean isRefreshTokenValid = jwtUtils.checkTokenValidity(refreshToken, false);

        // access còn hạn, refresh hết hạn
        if(isAccessTokenVallid && !isRefreshTokenValid){
            jwtUtils.saveTokenInBlackList(accessToken, true);
        }
        else if(isAccessTokenVallid && isRefreshTokenValid){
            jwtUtils.saveTokenInBlackList(accessToken, true);
            jwtUtils.saveTokenInBlackList(refreshToken, false);
        }
        else if(!isAccessTokenVallid && isRefreshTokenValid){
            jwtUtils.saveTokenInBlackList(refreshToken, false);
        }
    }
}
