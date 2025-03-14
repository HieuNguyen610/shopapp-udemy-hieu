package hieu.shopappudemyhoang.repository;

import hieu.shopappudemyhoang.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    int countOrderByUserId(Long userId);

    List<Order> findOrdersByUserId(Long userId);

    @Query("SELECT a FROM Order a WHERE a.fullName iLIKE %:keyword% AND a.user.id = :userId")
    Page<Order> searchOrders(@Param("keyword") String keyword, @Param("userId") Long userId, Pageable pageable);


    @Query("select count(*) from Order a where a.fullName ilike %:keyword% and a.user.id  = :userId")
    int countOrders(@Param("keyword")String keyword,@Param("userId") Long userId);
}
