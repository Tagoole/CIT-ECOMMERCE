package com.ecommerce.demo.features.UserProfile.controller;


import com.ecommerce.demo.features.UserProfile.ApiResponse;
import com.ecommerce.demo.features.UserProfile.dto.UserRequestFull;
import com.ecommerce.demo.features.UserProfile.dto.UserResponse;
import com.ecommerce.demo.features.UserProfile.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> addUser(
            @RequestBody UserRequestFull userRequestFull
            ){
        ApiResponse<UserResponse> apiResponse = userService.addUser(userRequestFull);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }



}
