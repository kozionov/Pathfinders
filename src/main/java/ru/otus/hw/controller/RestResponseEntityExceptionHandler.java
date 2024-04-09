package ru.otus.hw.controller;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        List<String> errorList = ex
//                .getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
//                .collect(Collectors.toList());
//        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
////        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
//        return new ResponseEntity(errorList, headers, status.value());
//
//    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> myHandler(Exception ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        System.out.println(ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "errorList");
//        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
        return new ResponseEntity("errorList", headers, status.value());

    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return super.handleErrorResponseException(ex, headers, status, request);
    }

    @Data
    public class ErrorDetails {
        private HttpStatus status;
        private String message;
        private List<String> errors;

        public ErrorDetails(HttpStatus status, String message, List<String> errors) {
            super();
            this.status = status;
            this.message = message;
            this.errors = errors;
        }

        public ErrorDetails(HttpStatus status, String message, String error) {
            super();
            this.status = status;
            this.message = message;
            errors = Arrays.asList(error);
        }
    }
}
