package uz.demo.app_hr_manager.service.product;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Attachment;

@Service
public interface AttachmentService {
    DataDto<Attachment> addAttachment(MultipartFile file);

    DataDto<String> getById(Long id, HttpServletResponse response);
}
