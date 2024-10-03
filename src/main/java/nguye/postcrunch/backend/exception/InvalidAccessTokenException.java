package nguye.postcrunch.backend.exception;

public class InvalidAccessTokenException extends RuntimeException {

  private final String code;
  private final String message;

  public InvalidAccessTokenException(final String message) {
    super(message);
    this.code = AppError.UNAUTHORIZED.getCode();
    this.message = AppError.UNAUTHORIZED.getMessage();
  }
}
