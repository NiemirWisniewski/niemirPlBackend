package niemir.exceptions.userException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import niemir.exceptions.ErrorMessageResponse;

@ControllerAdvice
public class UserExceptionAdvisor {

    private static final Logger LOG = LoggerFactory.getLogger(UserExceptionAdvisor.class);

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse userNotFound(UserNotFoundException exception){
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }

    @ExceptionHandler({UsernameIsTakenException.class})
    @ResponseBody
    public ResponseEntity<String> usernameIsTakenException(UsernameIsTakenException exception){
        LOG.error(exception.getMessage(), exception);
        System.out.println(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({EmailIsTakenException.class})
    @ResponseBody
    public ResponseEntity<String> EmailIsTakenException(EmailIsTakenException exception){
        LOG.error(exception.getMessage(), exception);
        System.out.println(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}
