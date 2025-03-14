package hieu.shopappudemyhoang.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.entity.OrderDetail;
import hieu.shopappudemyhoang.repository.OrderDetailRepository;
import hieu.shopappudemyhoang.request.OrderDetailCreateRequest;
import hieu.shopappudemyhoang.response.OrderDetailResponse;
import hieu.shopappudemyhoang.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ObjectMapper objectMapper;

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailCreateRequest request) {
        OrderDetail newOrderDetail =  convertRequestToEntity(request);

        OrderDetail savedOrderDetail = orderDetailRepository.save(newOrderDetail);
        return convertEntityToResponse(savedOrderDetail);
    }

    private OrderDetail convertRequestToEntity(OrderDetailCreateRequest request) {
        return objectMapper.convertValue(request, OrderDetail.class);
    }

    private OrderDetailResponse convertEntityToResponse(OrderDetail entity) {
        return objectMapper.convertValue(entity, OrderDetailResponse.class);
    }
}
