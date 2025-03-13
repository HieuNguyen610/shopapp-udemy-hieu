package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.request.UserLoginRequest;
import hieu.shopappudemyhoang.request.UserRegisterRequest;
import hieu.shopappudemyhoang.response.UserResponse;
import jakarta.validation.Valid;

public interface UserService{
    UserResponse register(@Valid UserRegisterRequest request);

    UserResponse login(@Valid UserLoginRequest request);
}
