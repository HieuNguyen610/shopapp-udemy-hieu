package hieu.shopappudemyhoang.service.impl;

import hieu.shopappudemyhoang.entity.Category;
import hieu.shopappudemyhoang.repository.CategoryRepository;
import hieu.shopappudemyhoang.request.CategoryCreateRequest;
import hieu.shopappudemyhoang.response.CategoryResponse;
import hieu.shopappudemyhoang.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryResponse addCategory(CategoryCreateRequest request) {

        String name;
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        } else {
            name = request.getName();
            if (categoryRepository.findByName(name).isPresent()) {
                throw new IllegalArgumentException("Category name already exists");
            }
        }

        Category category = Category.builder()
                .name(name)
                .build();
        return convertEntityToResponse(categoryRepository.save(category));
    }

    private CategoryResponse convertEntityToResponse(Category entity) {
        return CategoryResponse.builder()
               .id(entity.getId())
               .name(entity.getName())
               .build();
    }
}
