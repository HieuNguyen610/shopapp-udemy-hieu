package hieu.shopappudemyhoang.response;

import hieu.shopappudemyhoang.entity.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponse {
    private Long id;

    private String name;
    private String description;
    private Float price;
    private Integer quantity;
    private String url;
    private Long categoryId;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
