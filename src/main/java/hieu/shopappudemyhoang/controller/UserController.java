package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.UserLoginRequest;
import hieu.shopappudemyhoang.request.UserRegisterRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.UserLoginResponse;
import hieu.shopappudemyhoang.response.UserResponse;
import hieu.shopappudemyhoang.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestParam(required = false) String input) {
        return ResponseEntity.ok("Hello, " + input);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(response)
                .message("Register user")
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UserLoginRequest request) {
        UserLoginResponse response = userService.login(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(response)
                .message("Login user")
                .build());
    }
}
