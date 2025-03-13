package hieu.shopappudemyhoang.response;

import hieu.shopappudemyhoang.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Long id;

    private String name;
    private String description;
    private Float price;
    private Integer quantity;
    private String url;
    private Category category;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
