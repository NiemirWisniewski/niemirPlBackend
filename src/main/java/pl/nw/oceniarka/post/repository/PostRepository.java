package pl.nw.oceniarka.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.nw.oceniarka.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
