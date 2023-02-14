package pl.nw.oceniarka.exception.userException;

public class EmailIsTakenException extends RuntimeException {

    public EmailIsTakenException(String email) {
        super(String.format("Email %s is already taken", email));
    }

}
