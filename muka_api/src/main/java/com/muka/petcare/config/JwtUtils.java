package com.muka.petcare.config;

import com.muka.petcare.entity.DisabledToken;
import com.muka.petcare.entity.User;
import com.muka.petcare.repository.DisabledTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {
    //customer
    private final JwtProperties jwtProperties;
    private final DisabledTokenRepository disabledTokenRepository;

    //hàm kiểm tra token đã hợp lệ : check tất cả các option như xác thực chữ ký, thời hạn, trong bảng database chưa?
    public boolean checkTokenValidFull(String token, boolean isAccesstoken) throws ParseException, JOSEException {
        JWTClaimsSet jwtClaimsSet = decodeToken(token, isAccesstoken);
        return jwtClaimsSet.getExpirationTime().after(new Date()) && !isTokenInBlackList(jwtClaimsSet.getJWTID());
    }

    //lưu token vào DB
    public void saveTokenInBlackList(String token, boolean isAcessToken) throws ParseException, JOSEException {
        JWTClaimsSet jwtClaimsSet = decodeToken(token, isAcessToken);
        if(disabledTokenRepository.findByTokenId(jwtClaimsSet.getJWTID()).isPresent()) return;

        DisabledToken disabledToken = new DisabledToken();
        disabledToken.setTokenId(jwtClaimsSet.getJWTID());
        disabledToken.setExpirationTime(jwtClaimsSet.getExpirationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        disabledTokenRepository.save(disabledToken);
    }

    //hàm kiểm tra token đã hợp lệ : chưa check DB
    public boolean checkTokenValidity(String token, boolean isAccessToken) throws ParseException, JOSEException {
        JWTClaimsSet jwtClaimsSet = decodeToken(token, isAccessToken);
        return jwtClaimsSet.getExpirationTime().after(new Date());
    }

    //kiểm tra token có đang ở database không
    public boolean isTokenInBlackList(String tokenId){
        return disabledTokenRepository.findByTokenId(tokenId).isPresent();
    }

    //tạo token trả về
    public String generateToken(User user, boolean isAcessToken){
        //thời gian dùng và khóa ký
        long validityMiliseconds = isAcessToken ? jwtProperties.getAccessTokenValidity() * 1000 : jwtProperties.getRefreshTokenValidity() * 1000;
        String secretKey = isAcessToken ? jwtProperties.getAccessTokenSecret() : jwtProperties.getRefreshTokenSecret();

        //tạo header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
//        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        //tạo payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("muka.com")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + validityMiliseconds))
                .jwtID(UUID.randomUUID().toString()) //tạo tokenId
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        //kí token :đùng thuật toán kí tạo và kí giải mã
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException("Failed to create JWT", e);
        }
    }

    //Giải mã token
    public JWTClaimsSet decodeToken(String token, boolean isAccessToken) throws ParseException, JOSEException {
        String secretKey = isAccessToken ? jwtProperties.getAccessTokenSecret() : jwtProperties.getRefreshTokenSecret();
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        //xác thực chữ ký
        signedJWT.verify(verifier);

        return signedJWT.getJWTClaimsSet();
    }

    //hàm tách role -> phân cách bởi " "
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getRoleName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getPermissionName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }
}
