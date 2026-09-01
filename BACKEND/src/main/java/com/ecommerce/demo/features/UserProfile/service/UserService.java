package com.ecommerce.demo.features.UserProfile.service;

import com.ecommerce.demo.features.UserProfile.ApiResponse;
import com.ecommerce.demo.features.UserProfile.dto.UserLoginDTO;
import com.ecommerce.demo.features.UserProfile.dto.UserLoginResponse;
import com.ecommerce.demo.features.UserProfile.dto.UserRequestFull;
import com.ecommerce.demo.features.UserProfile.dto.UserResponse;
import com.ecommerce.demo.features.UserProfile.mapper.UserMapper;
import com.ecommerce.demo.features.UserProfile.model.UserModel;
import com.ecommerce.demo.features.UserProfile.page.response.PageResponse;
import com.ecommerce.demo.features.UserProfile.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }


    public ApiResponse<UserResponse> addUser(UserRequestFull userRequestFull){
        UserModel user = userMapper.toUserModel(userRequestFull);
        UserResponse userResponse = userMapper.toResponse(userRepository.save(user));
        return new ApiResponse<>("SUCCESS","User Added Successfully",userResponse);
    }

    public ApiResponse<UserResponse> getUserById(Long id) {
        UserModel  user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("USER NOT FOUND"));
        return new  ApiResponse<UserResponse>("SUCCESS","Student Item",userMapper.toResponse(user));
    }


    public ApiResponse<PageResponse<List<UserResponse>>> getAllUsers(int page, int pageSize){
        int zeroBasedPage = Math.max(0,page-1);
        Pageable pageable = PageRequest.of(zeroBasedPage,pageSize, Sort.by("id").ascending());
        Page<UserModel> userPage = userRepository.findAll(pageable);
        List<UserResponse> userResponseList = userPage.getContent()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        PageResponse<List<UserResponse>> pageResponse = new PageResponse<>(userResponseList,
                userPage.getTotalPages(),
                page,
                userPage.getTotalElements(),
                userPage.isLast()
                );
        return new ApiResponse<>("SUCCESS","Page Of Users",pageResponse);
    }



    public ApiResponse<UserLoginResponse> login(UserLoginDTO userLoginDTO){
        UserModel user = userRepository.findByUsername(userLoginDTO.username())
                .orElse(null);

        if(user == null ||  !Objects.equals(userLoginDTO.password(), user.getPassword())){
            return new ApiResponse<>("ERROR",
                    "Wrong username or password",
                        null
                    );
        }
        return new ApiResponse<>("SUCCESS",
                "Login Successful",
                userMapper.toLoginResponse(user,"Login Successful")
        );

    }


}
