package hieu.shopappudemyhoang.repository;

import hieu.shopappudemyhoang.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    int countOrderDetailByOrderId(Long orderId);

    List<OrderDetail> findOrderDetailByOrderId(Long orderId);
}
