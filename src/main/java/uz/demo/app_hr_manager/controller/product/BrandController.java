package uz.demo.app_hr_manager.controller.product;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.demo.app_hr_manager.dto.request.BrandDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Brand;
import uz.demo.app_hr_manager.service.product.BrandService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/brand")
public class BrandController {

    private final BrandService brandService;

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("/add")
    public ResponseEntity<DataDto<Brand>> addBrand(@RequestBody BrandDto brandDto) {
        return ResponseEntity.ok(brandService.addBrand(brandDto));
    }
}
