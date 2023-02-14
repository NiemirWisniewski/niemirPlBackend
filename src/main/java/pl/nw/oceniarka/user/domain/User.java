package pl.nw.oceniarka.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import pl.nw.oceniarka.comment.domain.Comment;
import pl.nw.oceniarka.post.domain.Post;
import pl.nw.oceniarka.user.domain.role.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Post> posts;

    @OneToMany(mappedBy = "author", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Comment> comments;


    public User(String username, String password, Role role, List<Post> postList) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.posts = postList;
    }

    public User(String username, String password, Role role, String email, List<Post> posts, List<Comment> comments) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.posts = posts;
        this.comments = comments;
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, Role role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public String getRoleAsString(){
        return this.role.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

/*spring.jpa.hibernate.ddl-auto=create-drop

        spring.datasource.url=jdbc:postgresql://localhost:5432/test
        spring.datasource.username=postgres
        spring.datasource.password=postgres
        spring.datasource.driver-class-name=org.postgresql.Driver
 */
