package uz.demo.app_hr_manager.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.demo.app_hr_manager.entity.product.VideoReview;

@Repository
public interface VideoReviewRepository extends JpaRepository<VideoReview, Long> {
}
