package nguye.postcrunch.backend.exception;

import lombok.Getter;

@Getter
public class InvalidRefreshTokenException extends RuntimeException {

  private final String code;
  private final String message;

  public InvalidRefreshTokenException() {
    this.code = AppError.RESOURCE_NOT_FOUND.getCode();
    this.message = AppError.RESOURCE_NOT_FOUND.getMessage();
  }

  public InvalidRefreshTokenException(final String message) {
    super(message);
    this.code = AppError.RESOURCE_NOT_FOUND.getCode();
    this.message = AppError.RESOURCE_NOT_FOUND.getMessage();
  }
}
