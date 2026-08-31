package com.ecommerce.demo.features.UserProfile.mapper;

import com.ecommerce.demo.features.UserProfile.dto.UserRequestFull;
import com.ecommerce.demo.features.UserProfile.dto.UserResponse;
import com.ecommerce.demo.features.UserProfile.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(UserModel user){
        return new UserResponse(
                user.getUsername(),
                user.getPhoneNumber(),
                user.getEmail()
        );
    }

    public UserModel toUserModel(UserRequestFull request){

        UserModel user = new UserModel();

        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPhoneNumber(request.phoneNumber());
        user.setUsername(request.username());
        return user;
    }
}
