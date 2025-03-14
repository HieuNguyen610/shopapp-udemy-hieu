package hieu.shopappudemyhoang.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.entity.Product;
import hieu.shopappudemyhoang.entity.ProductImage;
import hieu.shopappudemyhoang.repository.CategoryRepository;
import hieu.shopappudemyhoang.repository.ProductImageRepository;
import hieu.shopappudemyhoang.repository.ProductRepository;
import hieu.shopappudemyhoang.request.ProductCreateRequest;
import hieu.shopappudemyhoang.request.ProductUpdateRequest;
import hieu.shopappudemyhoang.response.ProductPagingResponse;
import hieu.shopappudemyhoang.response.ProductResponse;
import hieu.shopappudemyhoang.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ObjectMapper objectMapper;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public ProductPagingResponse getAllProducts() {
        int count = (int) productRepository.count();
        List<Product> products = productRepository.findAll(Sort.by("id"));
        return ProductPagingResponse.builder()
                .count(count)
                .products(convertEntitiesToResponses(products))
                .build();
    }

    @Override
    public ProductResponse findById(Long id) {
        return convertEntityToResponse(findProductById(id));
    }

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) throws IOException {
        if ( request == null) {
            return null;
        }
        String imageUrl = storeFile(request.getFile());

        Optional<Product> productOptional = productRepository.findByName(request.getName());
        if (productOptional.isPresent()) {
            throw new IllegalArgumentException("Product name = " + request.getName() + " already existed");
        }
        Product product = Product.builder()
                .price(request.getPrice())
                .url(request.getUrl())
                .createdAt(LocalDateTime.now())
                .name(request.getName())
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .active(Boolean.TRUE)
                .category(categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new IllegalArgumentException("Category id not exist")))
                .build();
        Product savedProduct = productRepository.save(product);

        ProductImage productImage = ProductImage.builder()
                .imageUrl(imageUrl)
                .product(savedProduct)
                .build();
        productImageRepository.save(productImage);

        return convertEntityToResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Product update request is empty");
        }
        Product product = findProductById(id);

        product.setUpdatedAt(LocalDateTime.now());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setUrl(request.getUrl());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setActive(request.isActive());
        Product savedProduct = productRepository.save(product);
        return convertEntityToResponse(savedProduct);
    }

    @Override
    public ProductResponse deleteById(Long id) {
        Product product = findProductById(id);
        product.setActive(Boolean.FALSE);
        Product updatedProduct = productRepository.save(product);
        return convertEntityToResponse(updatedProduct);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product id = " + id + " not found"));
    }

    private Product findProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Product name = " + name + " not yet existed"));
    }

    private ProductResponse convertEntityToResponse(Product entity) {
        ProductResponse response =objectMapper.convertValue(entity, ProductResponse.class);
        return response;
    }

    private List<ProductResponse> convertEntitiesToResponses(List<Product> entities) {
        return entities.stream()
               .map(this::convertEntityToResponse)
               .collect(toList());
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
