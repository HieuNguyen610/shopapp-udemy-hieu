package hieu.shopappudemyhoang.response;

import hieu.shopappudemyhoang.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryPagingResponse {
    private int count;
    private List<Category> categories;
}
