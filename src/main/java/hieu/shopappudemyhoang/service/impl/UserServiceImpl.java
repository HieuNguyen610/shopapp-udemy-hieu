package hieu.shopappudemyhoang.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.config.JwtTokenUtils;
import hieu.shopappudemyhoang.entity.Role;
import hieu.shopappudemyhoang.entity.User;
import hieu.shopappudemyhoang.repository.RoleRepository;
import hieu.shopappudemyhoang.repository.UserRepository;
import hieu.shopappudemyhoang.request.UserLoginRequest;
import hieu.shopappudemyhoang.request.UserRegisterRequest;
import hieu.shopappudemyhoang.response.UserLoginResponse;
import hieu.shopappudemyhoang.response.UserResponse;
import hieu.shopappudemyhoang.service.RoleService;
import hieu.shopappudemyhoang.service.UserService;
import hieu.shopappudemyhoang.utils.EntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final EntityConverter entityConverter;

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
    public UserLoginResponse login(UserLoginRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Invalid login request");
        }

        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(()-> new IllegalStateException("User with provided phone number not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Invalid password");
        }

        return UserLoginResponse.builder()
                .token(jwtTokenUtils.generateToken(user))
                .build();
    }

    private User convertRequestToEntity(UserRegisterRequest request) {
        return objectMapper.convertValue(request, User.class);
    }

    private UserResponse convertEntityToResponse(User entity) {
        UserResponse response = objectMapper.convertValue(entity, UserResponse.class);
        response.setRoles(entityConverter.convertRolesEntityToResponses(entity.getRoles()));
        return response;
    }

    private User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()-> new IllegalArgumentException("User with provided phone number not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return findByPhoneNumber(phoneNumber);
    }
}
