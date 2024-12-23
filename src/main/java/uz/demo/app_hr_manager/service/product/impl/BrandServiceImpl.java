package uz.demo.app_hr_manager.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.demo.app_hr_manager.dto.request.BrandDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Brand;
import uz.demo.app_hr_manager.exceptions.BadRequestException;
import uz.demo.app_hr_manager.mapper.BrandMapper;
import uz.demo.app_hr_manager.repository.product.BrandRepository;
import uz.demo.app_hr_manager.service.product.BrandService;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public DataDto<Brand> addBrand(BrandDto brandDto) {
        if (brandRepository.existsByName(brandDto.getName())) {
            throw new BadRequestException("This brand is already available");
        }

        Brand brand = brandMapper.toEntity(brandDto);
        Brand savedBrand = brandRepository.save(brand);

        return new DataDto<>(savedBrand);
    }
}
