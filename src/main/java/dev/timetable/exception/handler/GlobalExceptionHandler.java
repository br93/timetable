package dev.timetable.exception.handler;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dev.timetable.exception.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String URI_BAD_REQUEST = "https://httpstatuses.com/400";
    private static final String URI_NOT_FOUND = "https://httpstatuses.com/404";

    @ExceptionHandler({ ConstraintViolationException.class })
    public ProblemDetail handleConstraintViolationException (ConstraintViolationException exception) {
        
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuilder builder = new StringBuilder();

        Optional<ConstraintViolation<?>> message = violations.stream().findFirst();
        if (message.isPresent()){
            builder.append(message.get().getMessage());
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, builder.toString());
        problemDetail.setType(URI.create(URI_BAD_REQUEST));

        return problemDetail;
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ProblemDetail handleEntityNotFoundException (EntityNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setType(URI.create(URI_NOT_FOUND));

        return problemDetail;
    }

}
