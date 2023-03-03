package niemir.exceptions.postException;

public class ProcessException extends Exception {

    public ProcessException() {
        super();
    }

    public ProcessException(String message, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }
}
