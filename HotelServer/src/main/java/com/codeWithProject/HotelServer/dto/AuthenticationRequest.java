package com.codeWithProject.HotelServer.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AuthenticationRequest {

    private String email;
    private String password;
}