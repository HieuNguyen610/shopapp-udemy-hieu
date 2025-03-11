package hieu.shopappudemyhoang.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCreateRequest {

    @Size(message = "Name must be between 3 and 200 characters")
    private String name;

    private String description;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 10000000, message = "Price must be less than or equal to 10,000,000")
    private Float price;

    @Min(value = 0, message = "Quantity must be greater or equal to 0")
    @Max(value = 10000000, message = "Quantity must be less than or equal to 10,000,000")
    private Integer quantity;

    private String url;

    @NotNull(message = "Category ID cannot be empty")
    @JsonProperty("category_id")
    private Long categoryId;
}
