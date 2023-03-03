package niemir.comment.repository;

import niemir.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    //@Query("SELECT c FROM Comment WHERE c.authorId=:author_id")
    List<Comment> findAllByAuthor(Long authorId);
}
