package com.muka.petcare.service;

import com.muka.petcare.config.JwtUtils;
import com.muka.petcare.dto.request.add_pet.CreatePetRequest;
import com.muka.petcare.dto.request.update_pet.UpdatePetRequest;
import com.muka.petcare.dto.response.add_pet.CreatePetResponse;
import com.muka.petcare.dto.response.info_homepet.InfoHomePetResponse;
import com.muka.petcare.dto.response.info_pet.InfoPetResponse;
import com.muka.petcare.entity.Pet;
import com.muka.petcare.entity.User;
import com.muka.petcare.exception.AppException;
import com.muka.petcare.exception.ErrorCode;
import com.muka.petcare.repository.PetRepository;
import com.muka.petcare.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    //thêm thú cưng
    @PreAuthorize("hasRole('CUSTOMER')")
    public CreatePetResponse addPet(CreatePetRequest request, String token) throws ParseException, JOSEException {
        String userName = jwtUtils.decodeToken(token, true).getSubject();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NAME_NOT_EXISTED));

        if(petRepository.findByPetName(request.getPetName()).isPresent()){
            throw new AppException(ErrorCode.PET_NAME_EXISTED);
        }

        String fileNamePic = "image_default_pet.jpg";
        if(request.getPetPicture() != null && !request.getPetPicture().isEmpty()){
            fileNamePic = System.currentTimeMillis() + "_" + request.getPetPicture().getOriginalFilename();
            Path imgPath = Paths.get("C:/Users/ADMIN/Desktop/hackathonsoict2024/Pet-Tracking-And-Monitoring-System-MukaPet/web_petto/public/pet/" + fileNamePic);
            try {
                Files.copy(request.getPetPicture().getInputStream(), imgPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }

        Pet pet = Pet.builder()
                .petName(request.getPetName())
                .breed(request.getBreed())
                .species(request.getSpecies())
                .weight(request.getWeight())
                .age(request.getAge())
                .petPicture("/pet/" + fileNamePic)
                .active(true)
                .user(user)
                .build();

        petRepository.save(pet);

        return modelMapper.map(pet, CreatePetResponse.class);
    }

    //hiển thị thông tin thú cưng
    @PreAuthorize("hasAnyAuthority('VIEW_PET')")
    public InfoPetResponse showInfoPet(String petName, String token) throws ParseException, JOSEException {
        String userName = jwtUtils.decodeToken(token, true).getSubject();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NAME_NOT_EXISTED));
        Pet pet = petRepository.findByPetName(petName).orElseThrow(() -> new AppException(ErrorCode.PET_NAME_NOT_EXISTED));

        if(!Objects.equals(user.getId(), pet.getUser().getId())){
            throw new AppException(ErrorCode.PET_NOT_OWNER);
        }

        return modelMapper.map(pet, InfoPetResponse.class);
    }

    //chính sửa thông tin thú cưng
    @PreAuthorize("hasAnyAuthority('MANAGE_PET')")
    public InfoPetResponse updateInfoPet(UpdatePetRequest request, String petName, String token) throws ParseException, JOSEException {
        String userName = jwtUtils.decodeToken(token, true).getSubject();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NAME_NOT_EXISTED));
        Pet pet = petRepository.findByPetName(petName).orElseThrow(() -> new AppException(ErrorCode.PET_NAME_NOT_EXISTED));

        if(!Objects.equals(user.getId(), pet.getUser().getId())){
            throw new AppException(ErrorCode.PET_NOT_OWNER);
        }

        if(petRepository.findByPetName(request.getPetName()).isPresent()){
            throw new AppException(ErrorCode.PET_NAME_EXISTED);
        }

        pet.setPetName(request.getPetName());
        pet.setAge(request.getAge());
        pet.setBreed(request.getBreed());
        pet.setSpecies(request.getSpecies());
        pet.setWeight(request.getWeight());

        petRepository.save(pet);

        return modelMapper.map(pet, InfoPetResponse.class);
    }

    // danh sach thu cung cua username
    @PreAuthorize("hasAnyAuthority('VIEW_PET')")
    public List<InfoHomePetResponse> showInfoHomePet(String token) throws ParseException, JOSEException {
        String userName = jwtUtils.decodeToken(token, true).getSubject();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NAME_NOT_EXISTED));

        List<Pet> pets = petRepository.findByUser(user);

        return pets.stream()
                .map(pet -> modelMapper.map(pet, InfoHomePetResponse.class))
                .collect(Collectors.toList());
    }
}
