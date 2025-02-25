package com.muka.petcare.service;

import com.muka.petcare.dto.request.create_account.CreateUserRequest;
import com.muka.petcare.dto.response.create_account.CreateUserResponse;
import com.muka.petcare.entity.Permission;
import com.muka.petcare.entity.Role;
import com.muka.petcare.entity.User;
import com.muka.petcare.exception.AppException;
import com.muka.petcare.exception.ErrorCode;
import com.muka.petcare.repository.RoleRepository;
import com.muka.petcare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    // đăng ký tài khoản
    public CreateUserResponse createUser(CreateUserRequest request){
        if(userRepository.findByUserName(request.getUserName()).isPresent()){
            throw new AppException(ErrorCode.USER_NAME_EXISTED);
        }

        if(userRepository.findByUserEmail(request.getUserEmail()).isPresent()){
            throw  new AppException(ErrorCode.USER_EMAIL_EXISTED);
        }

        if(userRepository.findByUserPhone(request.getUserPhone()).isPresent()){
            throw  new AppException(ErrorCode.USER_PHONE_EXISTED);
        }

        //create new account
        //gán role
        Set<Role> roles = new HashSet<>();
        String roleName = request.getRole().toUpperCase();
        Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
        if(roleOptional.isEmpty()){
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        else {
            roles.add(roleOptional.get());
        }
        User user = User.builder()
                .userEmail(request.getUserEmail())
                .userName(request.getUserName())
                .userFullName(request.getUserFullName())
                .userPhone(request.getUserPhone())
                .profilePicture("/static/images/user/profile/profile_default.png")
                .roles(roles)
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .active(true)
                .build();

        //lưu vào DB
        User savedUser = userRepository.save(user);

        CreateUserResponse createUserResponse = modelMapper.map(savedUser, CreateUserResponse.class);

        //cung cấp thông tin các role và permission
        Set<String> rolesAndPermissions = new HashSet<>();
        for(Role role : savedUser.getRoles()){
            rolesAndPermissions.add("ROLE_" + role.getRoleName());

            List<Permission> permissions = roleRepository.findPermissionsByRoleName(role.getRoleName());

            for(Permission permission : permissions){
                rolesAndPermissions.add(permission.getPermissionName());
            }
        }

        createUserResponse.setRolesAndPermissions(rolesAndPermissions);

        return createUserResponse;
    }
}
