package web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import web.dto.response.ResponseDto;
import web.exception.*;
import web.validation.error.ValidationError;


@ControllerAdvice
@Slf4j
public class ExceptionHandlerController{

  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<ResponseDto> invalidDataException(InvalidDataException e) {
    log.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto
            .builder()
            .errorMessage(e.getMessage())
            .build());
  }

  @ExceptionHandler(AccessErrorException.class)
  public ResponseEntity<ResponseDto> accessErrorException(AccessErrorException e) {
    log.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseDto
            .builder()
            .errorMessage("Ошибка доступа: " + e.getMessage())
            .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseDto> exception(Exception ex) {
    log.error(String.valueOf(ex));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto
            .builder()
            .errorMessage("Произошла непредвиденная ошибка: " + ex.getMessage())
            .build());
  }

  @ExceptionHandler(AuthInvalidException.class)
  public ResponseEntity<ResponseDto> badCredentialsException(AuthInvalidException ex) {
    log.error(String.valueOf(ex));
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto
            .builder()
            .errorMessage(ex.getMessage())
            .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto> validation(MethodArgumentNotValidException ex) {
    ValidationError validationError = new ValidationError();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String errorMessage = error.getDefaultMessage();
      validationError.setMessage(errorMessage);
    });
    log.warn(String.valueOf(validationError));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto
            .builder()
            .errorMessage(validationError.getMessage())
            .build());
  }

  @ExceptionHandler(NoDataFoundException.class)
  public ResponseEntity<ResponseDto> dataException(NoDataFoundException e) {
    log.warn(e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto
            .builder()
            .errorMessage(e.getMessage())
            .build());
  }
}