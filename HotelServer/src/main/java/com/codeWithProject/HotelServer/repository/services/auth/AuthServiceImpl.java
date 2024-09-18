package com.codeWithProject.HotelServer.repository.services.auth;

import com.codeWithProject.HotelServer.dto.SignupRequest;
import com.codeWithProject.HotelServer.dto.UserDto;
import com.codeWithProject.HotelServer.entity.User;
import com.codeWithProject.HotelServer.enums.UserRole;
import com.codeWithProject.HotelServer.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount() {
        try {
            Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
            if (adminAccount.isEmpty()) {
                User user = new User();
                user.setEmail("admin@test.com");
                user.setName("Admin");
                user.setUserRole(UserRole.ADMIN);
                user.setPassword(new BCryptPasswordEncoder().encode("admin"));
                userRepository.save(user);
                System.out.println("Admin account created");
            } else {
                System.out.println("Admin account already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error creating admin account: " + e.getMessage());
        }
    }

    public UserDto createUser(SignupRequest signupRequest) {
        if (userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()) {
            throw new EntityExistsException("User already exists" + signupRequest.getEmail());
        }
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }


}
