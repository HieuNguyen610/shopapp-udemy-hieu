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

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("fullname")
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String note;

    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    private OrderStatus status; // Should be an ENUM: pending, delivering, cancel, done

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDateTime shippingDate;

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("payment_method")
    private String paymentMethod;
}
