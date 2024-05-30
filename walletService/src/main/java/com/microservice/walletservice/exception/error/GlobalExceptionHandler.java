package com.microservice.walletservice.exception.error;

import com.microservice.walletservice.exception.ChangeException;
import com.microservice.walletservice.exception.CreationException;
import com.microservice.walletservice.exception.WalletNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(final WalletNotFoundException ex) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Wallet Not Found", details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // handleCreationException : triggers when there is bad data to create wallet
    @ExceptionHandler(CreationException.class)
    public ResponseEntity<Object> handleCreationException(final CreationException ex) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Invalid data to create wallet", details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    // handleChangeException : triggers when there is bad data to change
    @ExceptionHandler(ChangeException.class)
    public ResponseEntity<Object> handleChangeException(final ChangeException ex) {

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Invalid data for changing", details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
