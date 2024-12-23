package uz.demo.app_hr_manager.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.demo.app_hr_manager.dto.request.ArticleDto;
import uz.demo.app_hr_manager.dto.response.AppResponse;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Article;
import uz.demo.app_hr_manager.service.product.ArticleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/article")
public class ArticleController {
    private final ArticleService articleService;

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("/add")
    public ResponseEntity<DataDto<Article>> addArticle(@RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.addRole(articleDto));
    }




}
