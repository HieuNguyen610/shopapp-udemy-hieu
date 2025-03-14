package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.OrderCreateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.OrderResponse;
import hieu.shopappudemyhoang.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        OrderResponse orderResponse = orderService.createOrder(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Add new category")
                .data(orderResponse)
                .build());
    }
}
