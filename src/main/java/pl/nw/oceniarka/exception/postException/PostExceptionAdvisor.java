package pl.nw.oceniarka.exception.postException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.nw.oceniarka.exception.ErrorMessageResponse;

public class PostExceptionAdvisor {

    private static final Logger LOG = LoggerFactory.getLogger(PostExceptionAdvisor.class);

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse userNotFound(PostNotFoundException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());

    }
}
