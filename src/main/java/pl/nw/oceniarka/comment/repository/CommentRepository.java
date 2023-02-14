package pl.nw.oceniarka.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nw.oceniarka.comment.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    //@Query("SELECT c FROM Comment WHERE c.authorId=:author_id")
    List<Comment> findAllByAuthor(Long authorId);
}
