package pl.nw.oceniarka.exception.postException;

import pl.nw.oceniarka.exception.userException.UserNotFoundException;

import java.util.function.Supplier;

public class PostExceptionSupplier {

    public static Supplier<PostNotFoundException> postNotFound(Long id){
        return () -> new PostNotFoundException(id);
    }

}
