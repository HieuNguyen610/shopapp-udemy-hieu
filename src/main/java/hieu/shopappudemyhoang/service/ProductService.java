package hieu.shopappudemyhoang.service;

import hieu.shopappudemyhoang.request.ProductCreateRequest;
import hieu.shopappudemyhoang.response.ProductPagingResponse;
import hieu.shopappudemyhoang.response.ProductResponse;

public interface ProductService {
    ProductPagingResponse getAllProducts();

    ProductResponse findById(Long id);

    ProductResponse createProduct(ProductCreateRequest request);
}
