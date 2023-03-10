package niemir.exceptions.commentException;

import java.util.function.Supplier;

public class CommentExceptionSupplier {

    public static Supplier<CommentNotFoundException> commentNotFound(Long id){
        return () -> new CommentNotFoundException(id);
    }

    public static Supplier<WrongRatingException> wrongRatingException() {
        return WrongRatingException::new;
    }
}

