package hieu.shopappudemyhoang.config;

import hieu.shopappudemyhoang.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        return ResponseEntity.ok(ApiResponse.builder()
                        .message(e.getMessage())
                        .data(e.getCause())
                .build());
    }
}
