package pl.nw.oceniarka.exception.userException;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super(String.format("User with id %d not found", id));
    }

    public UserNotFoundException(String username){
        super(String.format("User with username %s not found", username));
    }
}
