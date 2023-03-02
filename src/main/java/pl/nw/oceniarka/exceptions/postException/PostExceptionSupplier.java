package pl.nw.oceniarka.exceptions.postException;

import java.util.function.Supplier;

public class PostExceptionSupplier {

    public static Supplier<PostNotFoundException> postNotFound(Long id){
        return () -> new PostNotFoundException(id);
    }

}
