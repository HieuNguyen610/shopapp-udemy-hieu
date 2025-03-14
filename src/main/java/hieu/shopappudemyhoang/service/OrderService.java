package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.request.OrderCreateRequest;
import hieu.shopappudemyhoang.request.OrderUpdateRequest;
import hieu.shopappudemyhoang.response.OrderPagingResponse;
import hieu.shopappudemyhoang.response.OrderResponse;
import jakarta.validation.Valid;

public interface OrderService {
    OrderResponse createOrder(@Valid OrderCreateRequest request);

    OrderPagingResponse getOrderByUserId(Long userId);

    OrderResponse updateOrder(Long orderId, @Valid OrderUpdateRequest request);

    OrderPagingResponse search(String keyword, Long userId, int page, int limit);
}
