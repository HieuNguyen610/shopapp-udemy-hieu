package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.CategoryCreateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.CategoryPagingResponse;
import hieu.shopappudemyhoang.response.CategoryResponse;
import hieu.shopappudemyhoang.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> getAllCategories() {
        CategoryPagingResponse categories = categoryService.findAll();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        log.info(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Get all categories")
                        .data(categories)
                .build());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody CategoryCreateRequest request) {
        CategoryResponse categoryResponse = categoryService.addCategory(request);
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Add new category")
                        .data(categoryResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryCreateRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        // Update category logic here
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Update category")
                        .data(response)
                        .build());
    }
}
