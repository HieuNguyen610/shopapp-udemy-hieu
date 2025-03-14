package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.OrderCreateRequest;
import hieu.shopappudemyhoang.request.OrderUpdateRequest;
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

    @PutMapping("/{orderId}")
    public ResponseEntity<ApiResponse> updateOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderUpdateRequest request) {
        OrderResponse response = orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Update order id = " + orderId)
                        .data(response)
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchOrders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        OrderPagingResponse response = orderService.search(keyword, userId, page, limit);
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Search orders")
                        .data(response)
                .build());
    }

}
