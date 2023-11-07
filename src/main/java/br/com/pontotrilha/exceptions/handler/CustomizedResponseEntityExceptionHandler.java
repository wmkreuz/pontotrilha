package br.com.pontotrilha.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.pontotrilha.exceptions.ExceptionReponse;
import br.com.pontotrilha.exceptions.RequiredObjectIsNullException;
import br.com.pontotrilha.exceptions.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionReponse> handleAllExceptionsEntity(
            Exception ex, WebRequest request) {
        ExceptionReponse exceptionReponse = new ExceptionReponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionReponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionReponse> handleNotFoundException(
            Exception ex, WebRequest request) {
        ExceptionReponse exceptionReponse = new ExceptionReponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionReponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(RequiredObjectIsNullException.class)
    public final ResponseEntity<ExceptionReponse> handleBadRequestException(
            Exception ex, WebRequest request) {
        ExceptionReponse exceptionReponse = new ExceptionReponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionReponse, HttpStatus.BAD_REQUEST);
    }

}
