package hieu.shopappudemyhoang.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hieu.shopappudemyhoang.entity.Product;
import hieu.shopappudemyhoang.repository.CategoryRepository;
import hieu.shopappudemyhoang.repository.ProductRepository;
import hieu.shopappudemyhoang.request.ProductCreateRequest;
import hieu.shopappudemyhoang.response.ProductPagingResponse;
import hieu.shopappudemyhoang.response.ProductResponse;
import hieu.shopappudemyhoang.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ObjectMapper objectMapper;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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
    public ProductResponse createProduct(ProductCreateRequest request) {
        if ( request == null) {
            return null;
        }
        findProductByName(request.getName());
        Product product = Product.builder()
                .price(request.getPrice())
                .url(request.getUrl())
                .created_at(LocalDateTime.now())
                .name(request.getName())
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .category(categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new IllegalArgumentException("Category id not exist")))
                .build();

        Product savedProduct = productRepository.save(product);
        return convertEntityToResponse(savedProduct);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product id = " + id + " not found"));
    }

    private Product findProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Product name = " + name + " already existed"));
    }

    private ProductResponse convertEntityToResponse(Product entity) {
        return objectMapper.convertValue(entity, ProductResponse.class);
    }

    private List<ProductResponse> convertEntitiesToResponses(List<Product> entities) {
        return entities.stream()
               .map(this::convertEntityToResponse)
               .collect(toList());
    }
}
