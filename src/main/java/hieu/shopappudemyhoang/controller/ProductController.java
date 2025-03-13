package hieu.shopappudemyhoang.controller;

import hieu.shopappudemyhoang.request.ProductCreateRequest;
import hieu.shopappudemyhoang.request.ProductUpdateRequest;
import hieu.shopappudemyhoang.response.ApiResponse;
import hieu.shopappudemyhoang.response.ProductPagingResponse;
import hieu.shopappudemyhoang.response.ProductResponse;
import hieu.shopappudemyhoang.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

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

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createProduct(@Valid @ModelAttribute ProductCreateRequest request) throws IOException {

        storeFile(request.getFile());
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(ApiResponse.builder()
                        .data(response)
                        .message("Create new product")
                .build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute  ProductUpdateRequest request) throws IOException {

        storeFile(request.getFile());

        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(response)
                .message("Create new product")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        ProductResponse response = productService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                        .data(response)
                        .message("Delete product with id = " + id)
                .build());
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (file != null) {
            if (file.getSize() > 10 * 1024 * 1024) {
                throw new IllegalArgumentException("File is larger than 10MB");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new  IllegalArgumentException("File must be an image");
            }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
        }
        return null;
    }
}
