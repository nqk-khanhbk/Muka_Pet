package com.muka.petcare.controller;

import com.muka.petcare.dto.request.add_alert.CreateAlertRequest;
import com.muka.petcare.dto.request.update_pet.UpdatePetRequest;
import com.muka.petcare.dto.response.ResponseData;
import com.muka.petcare.dto.response.add_alert.CreateAlertResponse;
import com.muka.petcare.dto.response.info_pet.InfoPetResponse;
import com.muka.petcare.service.AlertService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/ai")
@Tag(name = "Chức năng của hệ thống ai")
@RequiredArgsConstructor
public class AiController {
    private final AlertService alertService;

    //thêm cảnh báo
    @PostMapping("/add-alert")
    @Operation(summary = "tạo cảnh báo", description = "tạo cảnh báo")
    public ResponseEntity<ResponseData<CreateAlertResponse>> addAlert(@Valid @RequestBody CreateAlertRequest request){
        CreateAlertResponse createAlertResponse = alertService.addAlert(request);

        ResponseData<CreateAlertResponse> responseData = ResponseData.<CreateAlertResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("add alert successfully")
                .data(createAlertResponse)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }
}
