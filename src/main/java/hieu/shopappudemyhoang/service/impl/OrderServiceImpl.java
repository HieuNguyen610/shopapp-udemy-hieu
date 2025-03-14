package hieu.shopappudemyhoang.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.entity.Order;
import hieu.shopappudemyhoang.repository.OrderRepository;
import hieu.shopappudemyhoang.repository.UserRepository;
import hieu.shopappudemyhoang.request.OrderCreateRequest;
import hieu.shopappudemyhoang.response.OrderResponse;
import hieu.shopappudemyhoang.response.UserResponse;
import hieu.shopappudemyhoang.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {
        if (!userRepository.existsById(request.getUserId())) {
            throw new IllegalArgumentException("User id of order request not found");
        }
        Order order = convertRequestToEntity(request);

        Order savedOrder = orderRepository.save(order);
        return convertEntityToResponse(savedOrder);
    }

    private OrderResponse convertEntityToResponse(Order entity) {
        OrderResponse response = objectMapper.convertValue(entity, OrderResponse.class);
        response.setUserId(entity.getUser().getId());
        response.setOrderDate(entity.getOrderDate());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setTotalMoney(entity.getTotalMoney());
        response.setShippingMethod(entity.getShippingMethod());
        response.setShippingAddress(entity.getShippingAddress());
        response.setTrackingNumber(entity.getTrackingNumber());
        response.setShippingDate(entity.getShippingDate());

//        response.setPaymentMethod(entity.getPaymentMethod());
        return response;
    }

    private Order convertRequestToEntity(OrderCreateRequest request) {
        Order order =  objectMapper.convertValue(request, Order.class);
        order.setStatus(request.getStatus());
        order.setShippingDate(LocalDateTime.now());
        order.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("User with id = " + request.getUserId() + " not found")));
        order.setOrderDate(LocalDateTime.now());
        order.setTotalMoney(request.getTotalMoney());
        order.setShippingMethod(request.getShippingMethod());
        order.setShippingAddress(request.getShippingAddress());
        order.setShippingDate(request.getShippingDate());
        order.setTrackingNumber(UUID.randomUUID().toString());
//        order.setPaymentMethod(request.getPaymentMethod());

        return order;
    }
}
