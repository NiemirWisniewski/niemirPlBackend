package niemir.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import niemir.comment.domain.Comment;
import niemir.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //removed CascadeType.PERSIST so commandlinerunner in main class could work
    @JoinColumn(name = "user_id")
    private User user;
    private String content;
    private String imageUrl;
    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    @OneToMany(cascade = {  CascadeType.REMOVE }) //removed CascadeType.PERSIST so commandlinerunner in main class could work
    private List<Comment> comment;

    public Post(User user, String content) {
        this.user = user;
        this.content = content;
        this.dateAdded = LocalDateTime.now();
    }

    public Post(User user, String content, String imageUrl) {
        this.user = user;
        this.content = content;
        this.imageUrl = imageUrl;
        this.dateAdded = LocalDateTime.now();
    }
}
