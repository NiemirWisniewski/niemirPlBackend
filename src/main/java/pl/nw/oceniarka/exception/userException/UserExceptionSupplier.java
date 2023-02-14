package pl.nw.oceniarka.exception.userException;

import java.util.function.Supplier;

public class UserExceptionSupplier {

    public static Supplier<UserNotFoundException> userNotFound(Long id){
        return () -> new UserNotFoundException(id);
    }

    public static Supplier<UserNotFoundException> userNotFound(String username) {return () -> new UserNotFoundException(username);
    }
}
