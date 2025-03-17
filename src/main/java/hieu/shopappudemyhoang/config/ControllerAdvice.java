package hieu.shopappudemyhoang.config;

import hieu.shopappudemyhoang.response.ApiResponse;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                        .message(e.getMessage())
                        .data(e.getClass())
                .build());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponse> handleJwtException(MalformedJwtException e) {
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .message(e.getMessage())
                .data(e.getClass())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException e) {

        return ResponseEntity.badRequest().body(ApiResponse.builder()
                       .message(e.getMessage())
                       .data(e.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).toList())
                       .build());
    }


}
