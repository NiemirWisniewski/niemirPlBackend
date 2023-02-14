package pl.nw.oceniarka.exception.commentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.nw.oceniarka.exception.ErrorMessageResponse;

public class CommentExceptionAdvisor {

    private static final Logger LOG = LoggerFactory.getLogger(CommentExceptionAdvisor.class);

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse commentNotFound(CommentNotFoundException exception){
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }

    @ExceptionHandler(WrongRatingException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorMessageResponse commentNotFound(WrongRatingException exception){
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }
}
