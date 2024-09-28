package nguye.postcrunch.backend.exception;

import lombok.Getter;

@Getter
public enum AppError {

  GENERIC_ERROR("POSTCRUNCH-001", "The system is unable to complete the request."),
  UNAUTHORIZED("POSTCRUNCH-002", "Please log in to complete the request."),
  RESOURCE_NOT_FOUND("POSTCRUNCH-003", "Requested resource not found.");

  private final String code;
  private final String message;

  AppError(final String code, final String message) {
    this.code = code;
    this.message = message;
  }
}
