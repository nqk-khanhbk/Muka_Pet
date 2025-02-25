package com.muka.petcare.service;

import com.muka.petcare.config.JwtUtils;
import com.muka.petcare.dto.request.add_alert.CreateAlertRequest;
import com.muka.petcare.dto.request.update_pet.UpdatePetRequest;
import com.muka.petcare.dto.response.add_alert.CreateAlertResponse;
import com.muka.petcare.dto.response.add_pet.CreatePetResponse;
import com.muka.petcare.dto.response.info_alert.InfoAlertResponse;
import com.muka.petcare.dto.response.update_alert.UpdateAlertResponse;
import com.muka.petcare.entity.Alert;
import com.muka.petcare.entity.Pet;
import com.muka.petcare.entity.User;
import com.muka.petcare.enums.StatusDB;
import com.muka.petcare.exception.AppException;
import com.muka.petcare.exception.ErrorCode;
import com.muka.petcare.repository.AlertRepository;
import com.muka.petcare.repository.PetRepository;
import com.muka.petcare.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final AlertRepository alertRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final JwtUtils jwtUtils;

    //danh sách các thông báo của user có username
    public List<InfoAlertResponse> showAlerts(String token) throws ParseException, JOSEException {
        String userName = jwtUtils.decodeToken(token, true).getSubject();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NAME_NOT_EXISTED));

        List<Alert> alerts = alertRepository.findAllByUserOrderByStatusAndTimestamp(user);

        // Chuyển đổi timestamp tại đây
        return alerts.stream()
                .map(alert -> {
                    InfoAlertResponse response = modelMapper.map(alert, InfoAlertResponse.class);
                    response.setTimestamp(alert.getTimestamp().toString());  // Chuyển đổi timestamp tại đây
                    return response;
                })
                .collect(Collectors.toList());
    }

    //thêm cảnh báo
    public CreateAlertResponse addAlert(CreateAlertRequest request){
        User user = userRepository.findByUserName(request.getUserName()).orElseThrow(() -> new AppException(ErrorCode.USER_NAME_NOT_EXISTED));

        Pet pet = petRepository.findByPetName(request.getPetName()).orElseThrow(() -> new AppException(ErrorCode.PET_NAME_NOT_EXISTED));

        if(!Objects.equals(pet.getUser().getId(), user.getId())){
            throw new AppException(ErrorCode.PET_NOT_OWNER);
        }

        Alert alert = Alert.builder()
                .pet(pet)
                .user(user)
                .alertMessage(request.getAlertMessage())
                .status(StatusDB.UNREAD.getTypeStatus())
                .location(request.getLocation())
                .build();

        alert = alertRepository.save(alert);
        return modelMapper.map(alert, CreateAlertResponse.class);
    }

    // cập nhật trạng thái thông báo
    public UpdateAlertResponse updateStatusAlert(String token, Integer alertId) throws ParseException, JOSEException {
        String userName = jwtUtils.decodeToken(token, true).getSubject();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NAME_NOT_EXISTED));
        Alert alert = alertRepository.findById(alertId).orElseThrow(() -> new AppException(ErrorCode.ALERT_NOT_EXISTED));

        if(!Objects.equals(alert.getUser().getId(), user.getId())){
            throw new AppException(ErrorCode.ALERT_NOT_OWNER);
        }

        // update status alert: toggle status
        if(Objects.equals(alert.getStatus(), StatusDB.READ.getTypeStatus())){
            alert.setStatus(StatusDB.UNREAD.getTypeStatus());
        }
        else {
            alert.setStatus(StatusDB.READ.getTypeStatus());
        }

        alertRepository.save(alert);

        return modelMapper.map(alert, UpdateAlertResponse.class);
    }
}
