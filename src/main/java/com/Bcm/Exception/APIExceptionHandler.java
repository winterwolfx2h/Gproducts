package com.Bcm.Exception;

import java.text.ParseException;
import java.util.NoSuchElementException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler(value = {NullPointerException.class})
  protected ResponseEntity<Object> nullPointerException(RuntimeException ex, WebRequest request) {
    return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler(value = {ParseException.class})
  protected ResponseEntity<Object> parseException(RuntimeException ex, WebRequest request) {
    var response = new ApiResponseDTO(HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString(), "Invalid Date Format", false);
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
  }

  @ExceptionHandler(value = {NoSuchElementException.class})
  protected ResponseEntity<Object> noSuchElementException(RuntimeException ex, WebRequest request) {
    var response =
        new ApiResponseDTO(HttpStatus.NOT_FOUND.toString(), "NoSuchElementException" + ex.getMessage(), false);
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = {AlreadyExistException.class})
  protected ResponseEntity<Object> alreadyExistExceptionException(RuntimeException ex, WebRequest request) {
    return buildResponseEntity(HttpStatus.ALREADY_REPORTED, ex.getMessage(), request);
  }

  @ExceptionHandler(value = {HttpMsgNotReadableException.class})
  protected ResponseEntity<Object> httpMsgNotReadableException(RuntimeException ex, WebRequest request) {
    return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler(value = {InvalidInput.class})
  protected ResponseEntity<Object> invalidInputException(RuntimeException ex, WebRequest request) {
    return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler(value = {InternalError.class})
  protected ResponseEntity<Object> internalError(RuntimeException ex, WebRequest request) {
    var response = new ApiResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "error in server", false);
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, WebRequest request) {
    var response = new ApiResponseDTO(status.toString(), message, false);
    return handleExceptionInternal(new RuntimeException(message), response, new HttpHeaders(), status, request);
  }
}
