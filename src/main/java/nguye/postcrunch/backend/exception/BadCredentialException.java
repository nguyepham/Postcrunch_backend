package nguye.postcrunch.backend.exception;

public class BadCredentialException extends RuntimeException {

  private final String code;
  private final String message;

  public BadCredentialException(final String message) {
    super(message);
    this.code = AppError.UNAUTHORIZED.getCode();
    this.message = message;
  }
}
