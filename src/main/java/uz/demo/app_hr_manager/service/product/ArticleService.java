package uz.demo.app_hr_manager.service.product;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.demo.app_hr_manager.dto.request.ArticleDto;
import uz.demo.app_hr_manager.dto.response.AppResponse;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Article;

@Service
public interface ArticleService {
    DataDto<Article> addRole(ArticleDto articleDto);
}
