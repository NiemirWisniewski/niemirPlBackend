package pl.nw.oceniarka.exceptions.userException;

public class EmailIsTakenException extends RuntimeException {

    public EmailIsTakenException(String email) {
        super(String.format("Email %s jest już zarejestrowany", email));
    }

}
