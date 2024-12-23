package uz.demo.app_hr_manager.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.demo.app_hr_manager.dto.request.BrandDto;
import uz.demo.app_hr_manager.entity.product.Brand;

@Component
@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toEntity(BrandDto brandDto);
}
