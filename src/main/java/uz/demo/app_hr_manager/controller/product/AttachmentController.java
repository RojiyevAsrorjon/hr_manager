package uz.demo.app_hr_manager.controller.product;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Attachment;
import uz.demo.app_hr_manager.service.product.AttachmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("/add")
    public ResponseEntity<DataDto<Attachment>> addAttachment(MultipartFile file) {
        return ResponseEntity.ok(attachmentService.addAttachment(file));
    }

    @PostMapping("/get-by-id/{id}")
    public ResponseEntity<DataDto<String>> getById(@PathVariable Long id, HttpServletResponse response) {
        return ResponseEntity.ok(attachmentService.getById(id, response));
    }
}
