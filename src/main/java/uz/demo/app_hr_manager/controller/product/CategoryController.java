package uz.demo.app_hr_manager.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.demo.app_hr_manager.dto.request.CategoryDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Category;
import uz.demo.app_hr_manager.service.product.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/product/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("/add")
    public ResponseEntity<DataDto<Category>> addCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.addCategory(categoryDto));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<DataDto<Category>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
