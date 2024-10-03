package nguye.postcrunch.backend.exception;

import nguye.postcrunch.backend.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestApiExceptionHandler {

  // Handle MethodArgumentNotValidException (for @Valid)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    // Collect field validation errors
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    // ex.printStackTrace();
    ErrorResponse error = new ErrorResponse()
        .errorCode(AppError.GENERIC_ERROR.getCode())
        .message(AppError.GENERIC_ERROR.getMessage())
        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception ex) {
    // ex.printStackTrace();
    ErrorResponse error = new ErrorResponse()
        .errorCode(AppError.RESOURCE_NOT_FOUND.getCode())
        .message(AppError.RESOURCE_NOT_FOUND.getMessage())
        .httpStatus(HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(Exception ex) {
    // ex.printStackTrace();
    ErrorResponse error = new ErrorResponse()
        .errorCode(AppError.RESOURCE_NOT_FOUND.getCode())
        .message("User not found")
        .httpStatus(HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadCredentialException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialException(Exception ex) {
    // ex.printStackTrace();
    ErrorResponse error = new ErrorResponse()
        .errorCode(AppError.UNAUTHORIZED.getCode())
        .message("Try another username or password.")
        .httpStatus(HttpStatus.UNAUTHORIZED.value());

    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InvalidAccessTokenException.class)
  public ResponseEntity<ErrorResponse> handleInvalidAccessTokenException(Exception ex) {
    // ex.printStackTrace();
    ErrorResponse error = new ErrorResponse()
        .errorCode(AppError.UNAUTHORIZED.getCode())
        .message("Invalid access token.")
        .httpStatus(HttpStatus.UNAUTHORIZED.value());

    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(Exception ex) {
    // ex.printStackTrace();
    ErrorResponse error = new ErrorResponse()
        .errorCode(AppError.BAD_REQUEST.getCode())
        .message(AppError.BAD_REQUEST.getMessage())
        .httpStatus(HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
