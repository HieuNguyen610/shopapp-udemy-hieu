package hieu.shopappudemyhoang.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDetailResponse {

    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("number_of_products")
    private Integer numberOfProducts;

    private Long price;

    @JsonProperty("total_money")
    private Long totalMoney;

    private String color;
}
