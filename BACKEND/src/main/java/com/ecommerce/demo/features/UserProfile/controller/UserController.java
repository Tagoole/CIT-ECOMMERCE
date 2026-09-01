package com.ecommerce.demo.features.UserProfile.controller;


import com.ecommerce.demo.features.UserProfile.ApiResponse;
import com.ecommerce.demo.features.UserProfile.dto.UserLoginDTO;
import com.ecommerce.demo.features.UserProfile.dto.UserLoginResponse;
import com.ecommerce.demo.features.UserProfile.dto.UserRequestFull;
import com.ecommerce.demo.features.UserProfile.dto.UserResponse;
import com.ecommerce.demo.features.UserProfile.page.response.PageResponse;
import com.ecommerce.demo.features.UserProfile.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<List<UserResponse>>>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        ApiResponse<PageResponse<List<UserResponse>>> apiResponse = userService.getAllUsers(page,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @PostMapping("login")
    public ResponseEntity<ApiResponse<UserLoginResponse>> login(
            @RequestBody UserLoginDTO userLoginDTO
            ){
        ApiResponse<UserLoginResponse> apiResponse = userService.login(userLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }





}
