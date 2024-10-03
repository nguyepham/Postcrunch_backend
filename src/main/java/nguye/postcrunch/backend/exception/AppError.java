package nguye.postcrunch.backend.exception;

import lombok.Getter;

@Getter
public enum AppError {

  GENERIC_ERROR("POSTCRUNCH-001", "The system is unable to complete the request."),
  BAD_REQUEST("POSTCRUNCH-002", "Bad request."),
  UNAUTHORIZED("POSTCRUNCH-003", "Please log in to complete the request."),
  RESOURCE_NOT_FOUND("POSTCRUNCH-004", "Requested resource not found."),
  RESOURCE_ALREADY_EXISTS("POSTCRUNCH-005", "Resource already exists.");

  private final String code;
  private final String message;

  AppError(final String code, final String message) {
    this.code = code;
    this.message = message;
  }
}
