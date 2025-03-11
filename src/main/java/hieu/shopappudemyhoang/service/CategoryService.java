package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.entity.Category;
import hieu.shopappudemyhoang.request.CategoryCreateRequest;
import hieu.shopappudemyhoang.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    CategoryResponse addCategory(CategoryCreateRequest request);
}
