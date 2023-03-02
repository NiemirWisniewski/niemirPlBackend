package pl.nw.oceniarka.comment.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nw.oceniarka.exceptions.commentException.WrongRatingException;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequest {

    private String comment;
    private Long authorId;
    private Double rate;

    @JsonCreator
    public CommentRequest(String comment, Long authorId, Double rate) {
        this.comment = comment;
        this.authorId = authorId;
        if (rate > 10.0 || rate <= 0) {
            throw new WrongRatingException();
        } else {
            this.rate = rate;
        }
    }
}
