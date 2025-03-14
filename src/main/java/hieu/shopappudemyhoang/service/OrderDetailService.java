package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.request.OrderDetailCreateRequest;
import hieu.shopappudemyhoang.response.OrderDetailResponse;

public interface OrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailCreateRequest request);
}
