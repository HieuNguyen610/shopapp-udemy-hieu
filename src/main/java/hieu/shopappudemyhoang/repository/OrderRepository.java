package hieu.shopappudemyhoang.repository;

import hieu.shopappudemyhoang.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    int countOrderByUserId(Long userId);

    List<Order> findOrdersByUserId(Long userId);
}
