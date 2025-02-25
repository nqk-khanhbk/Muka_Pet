package com.muka.petcare.controller;

import com.muka.petcare.dto.request.add_pet.CreatePetRequest;
import com.muka.petcare.dto.request.update_pet.UpdatePetRequest;
import com.muka.petcare.dto.response.ResponseData;
import com.muka.petcare.dto.response.add_pet.CreatePetResponse;
import com.muka.petcare.dto.response.info_alert.InfoAlertResponse;
import com.muka.petcare.dto.response.info_homepet.InfoHomePetResponse;
import com.muka.petcare.dto.response.info_pet.InfoPetResponse;
import com.muka.petcare.dto.response.update_alert.UpdateAlertResponse;
import com.muka.petcare.service.AlertService;
import com.muka.petcare.service.PetService;
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
import java.util.List;

@RestController
@RequestMapping("/myhome")
@Tag(name = "Chức năng người dùng")
@RequiredArgsConstructor
public class UserControlller {
    private final PetService petService;
    private final AlertService alertService;

    // danh sách pets
    @GetMapping("/pet")
    @Operation(summary = "danh sách thú nuôi", description = "danh sách thú nuôi hiện có")
    public ResponseEntity<ResponseData<List<InfoHomePetResponse>>> showInfoHomePet(@RequestHeader("Authorization") String token) throws ParseException, JOSEException {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        List<InfoHomePetResponse> infoHomePetResponses = petService.showInfoHomePet(token);

        ResponseData<List<InfoHomePetResponse>> responseData = ResponseData.<List<InfoHomePetResponse>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("show info home pet successfully")
                .data(infoHomePetResponses)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //thêm pet
    @PostMapping("/pet/add")
    @Operation(summary = "thêm thú nuôi", description = "lưu thông tin thú cưng vào database")
    public ResponseEntity<ResponseData<CreatePetResponse>> addPet(@Valid @ModelAttribute CreatePetRequest request, @RequestHeader("Authorization") String token) throws ParseException, JOSEException {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        CreatePetResponse createPetResponse = petService.addPet(request, token);

        ResponseData<CreatePetResponse> responseData = ResponseData.<CreatePetResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("add my pet successfully")
                .data(createPetResponse)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    //hiển thị thông tin của pet
    @GetMapping("/pet/{pet-name}")
    @Operation(summary = "hiển thị thông tin thú nuôi", description = "hiển thị thông tin thú nuôi bởi tên riêng của chúng: pet_name. Chú ý: chưa phân quyền chính xác cho role VETERINARIAN")
    public ResponseEntity<ResponseData<InfoPetResponse>> showInfoPet(@PathVariable("pet-name") String petName, @RequestHeader("Authorization") String token) throws ParseException, JOSEException {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        InfoPetResponse infoPetResponse = petService.showInfoPet(petName, token);

        ResponseData<InfoPetResponse> responseData = ResponseData.<InfoPetResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("show info my pet successfully")
                .data(infoPetResponse)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //chỉnh sửa thông tin của pet
    @PutMapping("/pet/{pet-name}/edit")
    @Operation(summary = "cập nhật thông tin thú nuôi", description = "cập nhật thông tin thú nuôi bởi tên riêng của chúng: pet_name. Chú ý: chưa phân quyền chính xác cho role VETERINARIAN, chưa thêm tính năng cập nhật ảnh")
    public ResponseEntity<ResponseData<InfoPetResponse>> updateInfoPet(@Valid @RequestBody UpdatePetRequest request, @PathVariable("pet-name") String petName, @RequestHeader("Authorization") String token) throws ParseException, JOSEException {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        InfoPetResponse infoPetResponse = petService.updateInfoPet(request, petName, token);

        ResponseData<InfoPetResponse> responseData = ResponseData.<InfoPetResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("update info my pet successfully")
                .data(infoPetResponse)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //Thông báo
    // hiển thị danh sách thông báo
    @GetMapping("/alert")
    @Operation(summary = "hiển thị danh sách thông báo", description = "hiển thị danh sách thông báo")
    public ResponseEntity<ResponseData<List<InfoAlertResponse>>> showAlert(@RequestHeader("Authorization") String token) throws ParseException, JOSEException {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        List<InfoAlertResponse> infoAlertResponses = alertService.showAlerts(token);

        ResponseData<List<InfoAlertResponse>> responseData = ResponseData.<List<InfoAlertResponse>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("show alerts successfully")
                .data(infoAlertResponses)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // cập nhật trạng thái của alert
    @PatchMapping("/alert/{alert-id}/toggle-status")
    @Operation(summary = "thay đổi trạng thái status", description = "")
    public ResponseEntity<ResponseData<UpdateAlertResponse>> showAlert(@RequestHeader("Authorization") String token, @PathVariable("alert-id") Integer alertId) throws ParseException, JOSEException {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        UpdateAlertResponse updateAlertResponse = alertService.updateStatusAlert(token, alertId);

        ResponseData<UpdateAlertResponse> responseData = ResponseData.<UpdateAlertResponse>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("update status alert successfully")
                .data(updateAlertResponse)
                .build();

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
