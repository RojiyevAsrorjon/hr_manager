package uz.demo.app_hr_manager.service.product;

import org.springframework.stereotype.Service;
import uz.demo.app_hr_manager.dto.request.CategoryDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Category;

@Service
public interface CategoryService {
    DataDto<Category> addCategory(CategoryDto categoryDto);

    DataDto<Category> getCategoryById(Long id);
}
