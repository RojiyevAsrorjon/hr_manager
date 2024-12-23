package uz.demo.app_hr_manager.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.demo.app_hr_manager.entity.product.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByArticleNumber(String articleNumber);
}
