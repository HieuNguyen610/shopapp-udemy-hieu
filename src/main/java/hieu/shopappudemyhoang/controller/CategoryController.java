package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.entity.Category;
import hieu.shopappudemyhoang.request.CategoryCreateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.CategoryResponse;
import hieu.shopappudemyhoang.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Get all categories")
                        .data(categories)
                .build());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody CategoryCreateRequest request) {
        CategoryResponse categoryResponse = categoryService.addCategory(request);
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Add new category")
                        .data(categoryResponse)
                .build());
    }
}
