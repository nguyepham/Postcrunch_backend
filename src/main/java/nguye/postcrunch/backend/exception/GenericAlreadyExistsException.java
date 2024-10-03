package nguye.postcrunch.backend.exception;

import lombok.Getter;

@Getter
public class GenericAlreadyExistsException extends RuntimeException {

  private final String code;
  private final String message;

  public GenericAlreadyExistsException(String message) {
    super(message);
    this.code = AppError.RESOURCE_ALREADY_EXISTS.getCode();
    this.message = AppError.RESOURCE_ALREADY_EXISTS.getMessage();
  }
}
