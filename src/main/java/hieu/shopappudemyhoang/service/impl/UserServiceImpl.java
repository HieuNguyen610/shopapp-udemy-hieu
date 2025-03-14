package hieu.shopappudemyhoang.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.entity.Role;
import hieu.shopappudemyhoang.entity.User;
import hieu.shopappudemyhoang.repository.RoleRepository;
import hieu.shopappudemyhoang.repository.UserRepository;
import hieu.shopappudemyhoang.request.UserLoginRequest;
import hieu.shopappudemyhoang.request.UserRegisterRequest;
import hieu.shopappudemyhoang.response.UserResponse;
import hieu.shopappudemyhoang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(UserRegisterRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request is null");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
           throw new IllegalArgumentException("User with phone number already exists");
        }
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(()-> new IllegalArgumentException("Role not existed"));
        roles.add(role);

        User user = User.builder()
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .isActive(request.isActive())
                .dateOfBirth(request.getDateOfBirth())
                .createdAt(LocalDateTime.now())
                .facebookAccountId(request.getFacebookAccountId())
                .googleAccountId(request.getGoogleAccountId())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        User savedUser = userRepository.save(user);
        return convertEntityToResponse(savedUser);
    }

    @Override
    public UserResponse login(UserLoginRequest request) {
        return UserResponse.builder().build();
    }

    private User convertRequestToEntity(UserRegisterRequest request) {
        return objectMapper.convertValue(request, User.class);
    }

    private UserResponse convertEntityToResponse(User entity) {
        return objectMapper.convertValue(entity, UserResponse.class);
    }

    private User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()-> new IllegalArgumentException("User with provided phone number not found"));
    }
}
