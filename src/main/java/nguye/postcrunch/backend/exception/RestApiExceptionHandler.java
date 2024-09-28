package nguye.postcrunch.backend.exception;

import nguye.postcrunch.backend.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(RestApiExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
//    ex.printStackTrace();
    ErrorResponse error = new ErrorResponse()
        .errorCode(AppError.GENERIC_ERROR.getCode())
        .message(AppError.GENERIC_ERROR.getMessage())
        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception ex) {
//    ex.printStackTrace();
    ErrorResponse error = new ErrorResponse()
        .errorCode(AppError.RESOURCE_NOT_FOUND.getCode())
        .message(AppError.RESOURCE_NOT_FOUND.getMessage())
        .httpStatus(HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }
}
