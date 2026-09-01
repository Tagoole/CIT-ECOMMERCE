package com.ecommerce.demo.features.UserProfile.dto;

public record UserRequestFull(String username, String password,
                            String confirmPassword,
                            String phoneNumber,
                            String email
                              ) {}
