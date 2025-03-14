package hieu.shopappudemyhoang.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.entity.Order;
import hieu.shopappudemyhoang.repository.OrderRepository;
import hieu.shopappudemyhoang.repository.UserRepository;
import hieu.shopappudemyhoang.request.OrderCreateRequest;
import hieu.shopappudemyhoang.request.OrderUpdateRequest;
import hieu.shopappudemyhoang.response.OrderPagingResponse;
import hieu.shopappudemyhoang.response.OrderResponse;
import hieu.shopappudemyhoang.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public OrderPagingResponse getOrderByUserId(Long userId) {
        int count = orderRepository.countOrderByUserId(userId);
        List<Order> orders = orderRepository.findOrdersByUserId(userId);

        OrderPagingResponse response = OrderPagingResponse.builder()
                .count(count)
                .orders(convertEntitiesToResponses(orders))
                .build();
        return response;
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        Order order = findOrderById(orderId);
        
        order.setFullName(request.getFullName());
        order.setEmail(request.getEmail());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setNote(request.getNote());
        order.setStatus(request.getStatus());
        order.setTotalMoney(request.getTotalMoney());
        order.setShippingMethod(request.getShippingMethod());
        order.setShippingAddress(request.getShippingAddress());
        order.setShippingDate(request.getShippingDate());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setUpdatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        return convertEntityToResponse(savedOrder);
    }

    @Override
    public OrderPagingResponse search(String keyword, Long userId, int page, int limit) {
        Pageable pageable = Pageable.ofSize(limit);
        int count = orderRepository.countOrders(keyword, userId);
        List<Order> orders = orderRepository.searchOrders(keyword, userId, pageable).stream().toList();
        OrderPagingResponse response = OrderPagingResponse.builder()
                .count(count)
                .orders(convertEntitiesToResponses(orders))
                .build();
        return response;
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
               .orElseThrow(() -> new IllegalArgumentException("Order with id = " + orderId + " not found"));
    }

    private List<OrderResponse> convertEntitiesToResponses(List<Order> orders) {
        return orders.stream()
               .map(this::convertEntityToResponse)
               .collect(Collectors.toList());
    }

    private OrderResponse convertEntityToResponse(Order entity) {
        OrderResponse response = objectMapper.convertValue(entity, OrderResponse.class);
        response.setUserId(entity.getUser().getId());
        return response;
    }

    private Order convertRequestToEntity(OrderCreateRequest request) {
        Order order =  objectMapper.convertValue(request, Order.class);
        order.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("User with id = " + request.getUserId() + " not found")));
        order.setShippingDate(LocalDateTime.now());
        order.setOrderDate(LocalDateTime.now());
        order.setTrackingNumber(UUID.randomUUID().toString());
        return order;
    }
}
