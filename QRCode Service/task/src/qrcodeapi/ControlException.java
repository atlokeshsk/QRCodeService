package qrcodeapi;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ControllerAdvice
public class ControlException extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var req = ((ServletWebRequest) request).getRequest();
        var uri = req.getRequestURI();
        if(uri.equals("/api/qrcode")){
            List<String> fieldOrder = List.of("contents", "size", "correction","type");
            var errors = new ArrayList<>(ex.getFieldErrors());
            System.out.println("before sorting");
            errors.forEach(fieldError -> System.out.println(fieldError.getField()));
            errors.sort(Comparator.comparingInt(o -> fieldOrder.indexOf(o.getField())));
            System.out.println("after sorting");
            errors.forEach(fieldError -> System.out.println(fieldError.getField()));
            var error = new ErrorMessage(errors.getFirst().getDefaultMessage());
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
