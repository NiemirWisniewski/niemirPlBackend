package pl.nw.oceniarka.exception.userException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.nw.oceniarka.exception.ErrorMessageResponse;

@ControllerAdvice
public class UserExceptionAdvisor {

    private static final Logger LOG = LoggerFactory.getLogger(UserExceptionAdvisor.class);

    @ExceptionHandler({UserNotFoundException.class, UsernameIsTakenException.class, EmailIsTakenException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse userNotFound(UserNotFoundException exception){
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }
}
