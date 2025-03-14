package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.OrderDetailCreateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.OrderDetailPagingResponse;
import hieu.shopappudemyhoang.response.OrderDetailResponse;
import hieu.shopappudemyhoang.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order_details")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createOrderDetail(@RequestBody OrderDetailCreateRequest request) {
        OrderDetailResponse response = orderDetailService.createOrderDetail(request);

        return ResponseEntity.ok(ApiResponse.builder()
                        .data(response)
                        .message("Create order detail successfully")
                .build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getAllOrderDetailsByOrderId(@PathVariable Long orderId) {
        OrderDetailPagingResponse response = orderDetailService.getAllOrderDetailsByOrderId(orderId);

        return ResponseEntity.ok(ApiResponse.builder()
                        .data(response)
                        .message("Get all order details by order ID successfully")
                .build());
    }
}
