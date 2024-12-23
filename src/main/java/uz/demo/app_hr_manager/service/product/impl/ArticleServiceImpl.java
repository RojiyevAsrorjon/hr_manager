package uz.demo.app_hr_manager.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.demo.app_hr_manager.dto.request.ArticleDto;
import uz.demo.app_hr_manager.dto.response.AppResponse;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Article;
import uz.demo.app_hr_manager.exceptions.BadRequestException;
import uz.demo.app_hr_manager.repository.product.ArticleRepository;
import uz.demo.app_hr_manager.service.product.ArticleService;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    @Override
    public DataDto<Article> addRole(ArticleDto articleDto) {
        if (articleRepository.existsByArticleNumber(articleDto.getArticleNumber())) {
            throw new BadRequestException("This number is already available!");
        }

        Article article = Article.builder()
                .articleNumber(articleDto.getArticleNumber())
                .build();

        Article savedArticle = articleRepository.save(article);
        return new DataDto<>(savedArticle);
    }
}
