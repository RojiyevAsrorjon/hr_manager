package uz.demo.app_hr_manager.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.demo.app_hr_manager.dto.request.CategoryDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Category;
import uz.demo.app_hr_manager.exceptions.BadRequestException;
import uz.demo.app_hr_manager.exceptions.NotFoundException;
import uz.demo.app_hr_manager.repository.product.CategoryRepository;
import uz.demo.app_hr_manager.service.product.CategoryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public DataDto<Category> addCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {
            throw new BadRequestException("This category already available");
        }
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setParentCategory(categoryDto.getParentCategory());
        category.setActive(true);
        Category save = categoryRepository.save(category);
        return new DataDto<>(save);
    }

    @Override
    public DataDto<Category> getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return new DataDto<>(optionalCategory.get());
        } else {
            throw new NotFoundException("Category with this id not found");
        }
    }
}
