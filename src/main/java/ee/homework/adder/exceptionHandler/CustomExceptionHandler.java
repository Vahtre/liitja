package ee.homework.adder.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = {RequestException.class, MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleExceptions(Exception e) {
        log.info("Received error: {}", e.getMessage());
        HttpStatus badRequestError = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(), badRequestError, ZonedDateTime.now(ZoneId.of("Europe/Tallinn")));
        log.info("Returning exception to user");
        return new ResponseEntity<>(apiException, badRequestError);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e){
        log.info("Recived error: {}",e.getMessage());
        String[] eMessage = e.getMessage().split(", ");
        String delicateErrorMessage = Arrays.stream(eMessage)
                                            .map(message -> message.split(": ")[1])
                                            .collect(Collectors.joining(" "));
        HttpStatus badRequestError = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(delicateErrorMessage, badRequestError,ZonedDateTime.now(ZoneId.of("Europe/Tallinn")));
        log.info("Returning exception to user");
        return new ResponseEntity<>(apiException, badRequestError);
    }
}
