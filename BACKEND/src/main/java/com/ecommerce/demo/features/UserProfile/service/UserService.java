package com.ecommerce.demo.features.UserProfile.service;

import com.ecommerce.demo.features.UserProfile.ApiResponse;
import com.ecommerce.demo.features.UserProfile.dto.UserRequestFull;
import com.ecommerce.demo.features.UserProfile.dto.UserResponse;
import com.ecommerce.demo.features.UserProfile.mapper.UserMapper;
import com.ecommerce.demo.features.UserProfile.model.UserModel;
import com.ecommerce.demo.features.UserProfile.repository.UserRepository;
import org.springframework.stereotype.Service;

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
}
