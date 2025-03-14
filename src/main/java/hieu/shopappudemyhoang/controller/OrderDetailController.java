package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.OrderDetailCreateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.OrderDetailResponse;
import hieu.shopappudemyhoang.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
