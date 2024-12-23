package uz.demo.app_hr_manager.service.product;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.demo.app_hr_manager.dto.request.BrandDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Brand;

@Service
public interface BrandService {
    DataDto<Brand> addBrand(BrandDto brandDto);
}
