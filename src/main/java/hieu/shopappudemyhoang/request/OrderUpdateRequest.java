package hieu.shopappudemyhoang.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import hieu.shopappudemyhoang.entity.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderUpdateRequest {

    @NotNull(message = "User ID is required")
    @JsonProperty("user_id")
    private Long userId;

    @NotBlank(message = "Full name is required")
    @JsonProperty("fullname")
    private String fullName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    private String note;

    @JsonProperty("status")
    private OrderStatus status;

    @NotNull(message = "Total money is required")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDateTime shippingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;
}

