package pl.nw.oceniarka.exception.userException;

public class UsernameIsTakenException extends RuntimeException{

    public UsernameIsTakenException(String username) {
        super(String.format("Username %s is already taken", username));
    }

}
