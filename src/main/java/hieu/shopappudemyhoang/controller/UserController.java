package hieu.shopappudemyhoang.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestParam(required = false) String input) {
        return ResponseEntity.ok("Hello, " + input);
    }
}
