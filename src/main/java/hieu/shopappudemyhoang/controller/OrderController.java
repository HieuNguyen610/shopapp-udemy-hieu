package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.OrderCreateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.OrderPagingResponse;
import hieu.shopappudemyhoang.response.OrderResponse;
import hieu.shopappudemyhoang.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@PathVariable Long userId) {
        OrderPagingResponse response = orderService.getOrderByUserId(userId);
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Get orders by user id = " + userId)
                        .data(response)
                .build());
    }

}
