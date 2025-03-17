package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.CategoryCreateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.CategoryPagingResponse;
import hieu.shopappudemyhoang.response.CategoryResponse;
import hieu.shopappudemyhoang.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        CategoryPagingResponse categories = categoryService.findAll();
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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryCreateRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        // Update category logic here
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Update category")
                        .data(response)
                        .build());
    }
}
