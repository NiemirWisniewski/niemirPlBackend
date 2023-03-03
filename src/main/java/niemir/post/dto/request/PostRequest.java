package niemir.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {

    private String content;
    private String image;
    private String imageUrl;
    private String author;

    public PostRequest(String content, String image, String author, String imageUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.author = author;
        this.image = image;
    }

    public PostRequest(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public PostRequest(String content, String image, String author) {
        this.content = content;
        this.image = image;
        this.author = author;
    }
}
