package uz.demo.app_hr_manager.exceptions.handler;


import io.jsonwebtoken.ExpiredJwtException;
import io.sentry.Sentry;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import uz.demo.app_hr_manager.dto.response.AppErrorDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.exceptions.BadRequestException;
import uz.demo.app_hr_manager.exceptions.ExceptionResponse;
import uz.demo.app_hr_manager.exceptions.NotFoundException;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public HttpEntity<?> handleAccessDeniedException(UsernameNotFoundException e) {
        Sentry.captureException(e);
        return ResponseEntity.ok(new DataDto<>(new AppErrorDto(HttpStatus.FORBIDDEN, e.getMessage())));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public HttpEntity<?> handleAccessDeniedException(ExpiredJwtException e) {
        Sentry.captureException(e);
        return ResponseEntity.ok(new DataDto<>(new AppErrorDto(HttpStatus.FORBIDDEN, e.getMessage())));
//        return ResponseEntity
//                .status(HttpStatus.FORBIDDEN)
//                .body(new ExceptionResponse(
//                        HttpStatus.FORBIDDEN,
//                        "Authentication token is expired!",
//                        LocalDateTime.now()
//                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        String errorMessage = String.format("Validation failed: '%s' for parameter '%s'", e.getValue(), e.getName());
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolation(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> String.format("'%s' : %s", violation.getPropertyPath(), violation.getMessage()))
                .collect(Collectors.joining(", "));

        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed: " + errorMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        String errorMessage = "Malformed JSON request or invalid data format";
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public DataDto<?> handleMethodArgumentTypeMismatch(BadRequestException e) {
        Sentry.captureException(e);
        return new DataDto<>(new AppErrorDto(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public DataDto<ExceptionResponse> handleMethodArgumentTypeMismatchRuntime(RuntimeException e) {
        return new DataDto<>(new AppErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationCredentialsNotFound(AuthenticationCredentialsNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.UNAUTHORIZED,
                "Authentication credentials are missing or invalid",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public DataDto<ExceptionResponse> handleNotFound(NotFoundException e) {
        return new DataDto<>(new AppErrorDto(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder msg = new StringBuilder("Check field(s) format: ");
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            msg.append(fieldName).append(" - ").append(errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        HttpStatus.BAD_REQUEST,
                        msg.toString(),
                        LocalDateTime.now()));
    }
}
