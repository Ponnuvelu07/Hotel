package com.codeWithProject.HotelServer.repository.services.auth;


import com.codeWithProject.HotelServer.dto.SignupRequest;
import com.codeWithProject.HotelServer.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
}
