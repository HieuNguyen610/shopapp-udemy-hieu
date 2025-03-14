package hieu.shopappudemyhoang.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hieu.shopappudemyhoang.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private Long userId;

    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String note;

    private LocalDateTime orderDate;

    private LocalDateTime updatedAt;

    private OrderStatus status; // Should be an ENUM: pending, delivering, cancel, done

    private Float totalMoney;

    private String shippingMethod;

    private String shippingAddress;

    private LocalDateTime shippingDate;

    private String trackingNumber;

    @JsonProperty("payment_method")
    private String paymentMethod;
}
