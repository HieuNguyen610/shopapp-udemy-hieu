package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.request.OrderCreateRequest;
import hieu.shopappudemyhoang.response.OrderPagingResponse;
import hieu.shopappudemyhoang.response.OrderResponse;
import jakarta.validation.Valid;

public interface OrderService {
    OrderResponse createOrder(@Valid OrderCreateRequest request);

    OrderPagingResponse getOrderByUserId(Long userId);
}
