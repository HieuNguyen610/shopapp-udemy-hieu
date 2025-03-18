package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.entity.User;
import hieu.shopappudemyhoang.request.UserLoginRequest;
import hieu.shopappudemyhoang.request.UserRegisterRequest;
import hieu.shopappudemyhoang.response.UserLoginResponse;
import hieu.shopappudemyhoang.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService{
    UserResponse register(@Valid UserRegisterRequest request);

    UserLoginResponse login(@Valid UserLoginRequest request);

    UserDetails loadUserByUsername(String phone);
}
