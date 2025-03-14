package hieu.shopappudemyhoang.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.entity.OrderDetail;
import hieu.shopappudemyhoang.repository.OrderDetailRepository;
import hieu.shopappudemyhoang.request.OrderDetailCreateRequest;
import hieu.shopappudemyhoang.response.OrderDetailPagingResponse;
import hieu.shopappudemyhoang.response.OrderDetailResponse;
import hieu.shopappudemyhoang.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public OrderDetailPagingResponse getAllOrderDetailsByOrderId(Long orderId) {

        int count = orderDetailRepository.countOrderDetailByOrderId(orderId);
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailByOrderId(orderId);

        OrderDetailPagingResponse response = OrderDetailPagingResponse.builder()
                .size(10)
                .page(0)
                .count(count)
                .orderId(orderId)
                .orderDetails(convertEntitiesToResponses(orderDetails))
                .build();
        return response;
    }

    private OrderDetail convertRequestToEntity(OrderDetailCreateRequest request) {
        return objectMapper.convertValue(request, OrderDetail.class);
    }

    private OrderDetailResponse convertEntityToResponse(OrderDetail entity) {
        return objectMapper.convertValue(entity, OrderDetailResponse.class);
    }

    private List<OrderDetailResponse> convertEntitiesToResponses(List<OrderDetail> entities) {
        return entities.stream()
               .map(this::convertEntityToResponse)
               .collect(Collectors.toList());
    }
}
