package com.muka.petcare.controller;

import com.muka.petcare.dto.request.auth.AuthenticationRequest;
import com.muka.petcare.dto.request.auth.LogoutRequest;
import com.muka.petcare.dto.request.auth.RefreshAccessTokenRequest;
import com.muka.petcare.dto.response.ResponseData;
import com.muka.petcare.dto.response.auth.AuthenticationResponse;
import com.muka.petcare.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@Tag(name = "Xác thực")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Đăng nhập", description = "tạo access token và refresh token")
    @PostMapping("/login")
    public ResponseEntity<ResponseData<AuthenticationResponse>> authenticate(@Valid @RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);

        ResponseData<AuthenticationResponse> responseData = ResponseData.<AuthenticationResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Authentication successful, token generated")
                .data(result)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @Operation(summary = "Đăng xuất", description = "tạo access token và refresh token")
    @PostMapping("/logout")
    public ResponseEntity<ResponseData<Void>> logout(@Valid @RequestBody LogoutRequest request) throws ParseException, JOSEException {
        String accessToken = request.getAccessToken();
        String refreshToken = request.getRefreshToken();
        authenticationService.logout(accessToken, refreshToken);

        ResponseData<Void> responseData = ResponseData.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Logout successful")
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @Operation(summary = "Làm mới access_token", description = "tạo access token và refresh token")
    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseData<String>> refreshAccessToken(@Valid @RequestBody RefreshAccessTokenRequest request) throws ParseException, JOSEException {
        var result = authenticationService.refreshAccessToken(request.getAccessToken(), request.getRefreshToken());

        ResponseData<String> responseData = ResponseData.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Refresh access token successful, token generated")
                .data(result)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
