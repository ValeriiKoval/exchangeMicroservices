package com.microservice.authservice.exception.error;

import com.microservice.authservice.exception.CustomException;
import com.microservice.authservice.exception.RefreshTokenException;
import com.microservice.authservice.exception.RegisterException;
import com.microservice.authservice.exception.RoleException;
import com.microservice.authservice.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            final HttpMediaTypeNotSupportedException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

        final List<String> details = new ArrayList<>();


        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        details.add(builder.toString());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Invalid JSON", details);

        return ResponseEntity.status(status).body(err);

    }

    // handleHttpMessageNotReadable : triggers when the JSON is malformed
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Malformed JSON request", details);


        return ResponseEntity.status(status).body(err);
    }

    // handleMethodArgumentNotValid : triggers when @Valid fails
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        final List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .toList();

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Validation Errors", details);

        return ResponseEntity.status(status).body(err);
    }

    // handleMissingServletRequestParameter : triggers when there are missing parameters
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getParameterName() + " parameter is missing");

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Missing Parameters", details);

        return ResponseEntity.status(status).body(err);
    }


    // handleUserNotFoundException : triggers when there is not resource with the specified ID in User
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException ex) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "User Not Found", details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // handleRoleException : triggers when there is not resource with the specified ID in Role
    @ExceptionHandler(RoleException.class)
    public ResponseEntity<Object> handleRoleException(final RoleException ex) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Role Not Found", details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // handleRefreshTokenException : triggers when there is not resource with the specified ID in RefreshToken
    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<Object> handleRefreshTokenException(final RefreshTokenException ex) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Refresh Token Not Found", details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // handleRegisterException : triggers when there is bad data for register user
    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<Object> handleRegisterException(final RegisterException ex) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Invalid user registration information", details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    // handleLockedException : triggers when user does not verify email
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Object> handleLockedException(final LockedException ex) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, LocalDateTime.now(),
                "You need to verify your email address before logging in", details);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(),
                "Internal server error", details);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
