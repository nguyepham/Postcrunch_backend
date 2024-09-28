package nguye.postcrunch.backend.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

  private final String code;
  private final String message;

  public ResourceNotFoundException() {
    this.code = AppError.RESOURCE_NOT_FOUND.getCode();
    this.message = AppError.RESOURCE_NOT_FOUND.getMessage();
  }
}
