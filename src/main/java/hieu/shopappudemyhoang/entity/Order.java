package hieu.shopappudemyhoang.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "fullname", length = 100, nullable = false, columnDefinition = "VARCHAR(100) DEFAULT ''")
    @JsonProperty("fullname")
    private String fullName;

    @Column(name = "email", length = 100, nullable = false, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String email;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "address", length = 200, nullable = false, columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String address = "";

    @Column(name = "note", length = 100, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String note = "";

    @Column(name = "order_date")
    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING) // Map enum as String in DB
    private OrderStatus status;

    @Column(name = "total_money", nullable = false)
    @JsonProperty("total_money")
    private Float totalMoney;

    @Column(name = "shipping_method", length = 100)
    @JsonProperty("shipping_method")
    private String shippingMethod;

    @Column(name = "shipping_address", length = 200)
    @JsonProperty("shipping_address")
    private String shippingAddress;

    @Column(name = "shipping_date")
    @JsonProperty("shipping_date")
    private LocalDateTime shippingDate;

    @Column(name = "tracking_number", length = 100)
    @JsonProperty("tracking_number")
    private String trackingNumber;

    @Column(name = "payment_method", length = 100)
    @JsonProperty("payment_method")
    private String paymentMethod;
}

