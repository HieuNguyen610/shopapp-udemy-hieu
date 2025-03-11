package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.entity.Category;
import hieu.shopappudemyhoang.request.CategoryCreateRequest;
import hieu.shopappudemyhoang.response.CategoryPagingResponse;
import hieu.shopappudemyhoang.response.CategoryResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    CategoryPagingResponse findAll();

    CategoryResponse addCategory(CategoryCreateRequest request);

    CategoryResponse updateCategory(Long id, @Valid CategoryCreateRequest request);
}
