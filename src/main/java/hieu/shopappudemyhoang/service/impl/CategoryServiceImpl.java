package hieu.shopappudemyhoang.service.impl;

import hieu.shopappudemyhoang.entity.Category;
import hieu.shopappudemyhoang.repository.CategoryRepository;
import hieu.shopappudemyhoang.request.CategoryCreateRequest;
import hieu.shopappudemyhoang.response.CategoryPagingResponse;
import hieu.shopappudemyhoang.response.CategoryResponse;
import hieu.shopappudemyhoang.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryPagingResponse findAll() {
        Sort sort = Sort.by(Sort.Order.by("id"));
        List<Category> categories = categoryRepository.findAll(sort);
        int count = (int) categoryRepository.count();
        return CategoryPagingResponse.builder()
                .count(count)
                .categories(categories)
                .build();
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

    @Override
    public CategoryResponse updateCategory(Long id, CategoryCreateRequest request) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Category not found id = " + id);
        }

        Category updatingCategory = category.get();
        String name;

        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Request name is invalid");
        } else {
            name = request.getName();
            if (categoryRepository.findByName(name).isPresent()) {
                throw new IllegalArgumentException("Category name already exists");
            }
        }
        updatingCategory.setName(name);
        return convertEntityToResponse(categoryRepository.save(updatingCategory));

    }

    private CategoryResponse convertEntityToResponse(Category entity) {
        return CategoryResponse.builder()
               .id(entity.getId())
               .name(entity.getName())
               .build();
    }
}
