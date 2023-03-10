package niemir.exceptions.commentException;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(Long id) {
        super(String.format("Comment with id %d not found", id));
    }

}
