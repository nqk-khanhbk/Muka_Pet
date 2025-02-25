package com.muka.petcare.controller;

import com.muka.petcare.dto.request.create_account.CreateUserRequest;
import com.muka.petcare.dto.response.ResponseData;
import com.muka.petcare.dto.response.create_account.CreateUserResponse;
import com.muka.petcare.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Tag(name = "Trang chủ")
public class HomeController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "đăng ký tài khoản", description = "đăng ký tài khoản cho người dùng")
    public ResponseEntity<ResponseData<CreateUserResponse>> register(@Valid @RequestBody CreateUserRequest request){
        CreateUserResponse createUserResponse = userService.createUser(request);

        ResponseData<CreateUserResponse> responseData = ResponseData.<CreateUserResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("register successfully")
                .data(createUserResponse)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }
}
