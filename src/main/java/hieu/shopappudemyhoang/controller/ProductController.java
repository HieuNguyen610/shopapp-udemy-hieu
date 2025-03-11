package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.ProductCreateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.ProductPagingResponse;
import hieu.shopappudemyhoang.response.ProductResponse;
import hieu.shopappudemyhoang.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllProducts() {
        ProductPagingResponse response = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Get all products")
               .data(response)
               .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ProductResponse response = productService.findById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                        .data(response)
                        .message("Find by id = " + id)
                .build());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(ApiResponse.builder()
                        .data(response)
                        .message("Create new product")
                .build());
    }
}
