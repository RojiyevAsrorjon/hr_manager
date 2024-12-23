package uz.demo.app_hr_manager.service.product.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.product.Attachment;
import uz.demo.app_hr_manager.entity.product.AttachmentContent;
import uz.demo.app_hr_manager.exceptions.BadRequestException;
import uz.demo.app_hr_manager.exceptions.NotFoundException;
import uz.demo.app_hr_manager.repository.product.AttachmentContextRepository;
import uz.demo.app_hr_manager.repository.product.AttachmentRepository;
import uz.demo.app_hr_manager.service.product.AttachmentService;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContextRepository attachmentContentRepository;


    @Override
    public DataDto<Attachment> addAttachment(MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            Long size = file.getSize();

            Optional<Attachment> optionalAttachment = attachmentRepository.findByFileNameAndSize(fileName, size);
            if (optionalAttachment.isPresent()) {
                throw new BadRequestException("File with this filename and size is available; ID:" + optionalAttachment.get().getId());
            }

            Attachment attachment = Attachment.builder()
                    .fileName(fileName)
                    .contentType(contentType)
                    .size(size)
                    .build();
            Attachment savedAttachment = attachmentRepository.save(attachment);

            try{
                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setBytes(file.getBytes());
                attachmentContent.setAttachmentId(savedAttachment.getId());
                System.out.println(attachmentContent.toString());
                attachmentContentRepository.save(attachmentContent);
                return new DataDto<>(savedAttachment);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new NotFoundException("File not found");
    }

    @Override
    public DataDto<String> getById(Long id, HttpServletResponse response) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
            if (optionalAttachmentContent.isPresent()) {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + optionalAttachment.get().getFileName() + "\"");
                try {
                    FileCopyUtils.copy(optionalAttachmentContent.get().getBytes(), response.getOutputStream());
                    return new DataDto<>("Downloaded");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                throw new NotFoundException("File not found");
            }
        }
        throw new NotFoundException("File not found");
    }

    @Override
    public ResponseEntity<DataDto<byte[]>> getImageById(Long id) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
            if (optionalAttachmentContent.isPresent()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=\"" + optionalAttachment.get().getFileName() + "\"");
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(new DataDto<>(optionalAttachmentContent.get().getBytes()));
            } else {
                throw new NotFoundException("File not found");
            }
        }
        throw new NotFoundException("File not found");
    }
}
