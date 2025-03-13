package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.request.ProductCreateRequest;
import hieu.shopappudemyhoang.request.ProductUpdateRequest;
import hieu.shopappudemyhoang.response.ProductPagingResponse;
import hieu.shopappudemyhoang.response.ProductResponse;
import jakarta.validation.Valid;

public interface ProductService {
    ProductPagingResponse getAllProducts();

    ProductResponse findById(Long id);

    ProductResponse createProduct(ProductCreateRequest request);

    ProductResponse updateProduct(Long id, ProductUpdateRequest request);

    ProductResponse deleteById(Long id);
}
