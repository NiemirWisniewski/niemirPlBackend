package pl.nw.oceniarka.exception.commentException;

public class WrongRatingException extends RuntimeException {

    public WrongRatingException() {
        super("Rating must be between 0 and 10");
    }
}
