package com.zn.iotproject.exception;

import com.zn.iotproject.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest req) {
        ExceptionDto.Response response = new ExceptionDto.Response(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> handleInvalidUsernameNotFoundException(Exception e, WebRequest req) {
        ExceptionDto.Response response = new ExceptionDto.Response(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidUserException.class)
    public final ResponseEntity<Object> handleInvalidUserException(Exception e, WebRequest req) {
        ExceptionDto.Response response = new ExceptionDto.Response(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidJwtException.class)
    public final ResponseEntity<Object> handleInvalidJwtException(Exception e, WebRequest req) {
        ExceptionDto.Response response = new ExceptionDto.Response(new Date(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
