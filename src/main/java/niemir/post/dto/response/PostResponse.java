package niemir.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class PostResponse {
    private Long id;
    private String author;
    private String content;
    private LocalDateTime dateAdded;
    private String imageUrl;

    public PostResponse(Long id, String author, String content, LocalDateTime dateAdded) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.dateAdded = dateAdded;
    }
}
