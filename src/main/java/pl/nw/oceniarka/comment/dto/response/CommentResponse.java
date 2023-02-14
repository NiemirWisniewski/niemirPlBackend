package pl.nw.oceniarka.comment.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentResponse {

    private Long id;
    private Long authorId;
    private String comment;
    private Double rate;
    private Long postId;
}
