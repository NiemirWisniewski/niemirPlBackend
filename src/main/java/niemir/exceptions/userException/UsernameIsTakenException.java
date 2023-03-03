package niemir.exceptions.userException;

public class UsernameIsTakenException extends RuntimeException{

    public UsernameIsTakenException(String username) {
        super(String.format("Nazwa użytkownika %s jest już zajęta", username));
    }
}
