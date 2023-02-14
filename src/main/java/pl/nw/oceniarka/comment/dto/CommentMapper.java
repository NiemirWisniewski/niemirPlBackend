package pl.nw.oceniarka.comment.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.nw.oceniarka.comment.dto.request.CommentRequest;
import pl.nw.oceniarka.comment.dto.response.CommentResponse;
import pl.nw.oceniarka.comment.domain.Comment;
import pl.nw.oceniarka.post.repository.PostRepository;
import pl.nw.oceniarka.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment toComment(CommentRequest commentRequest, Long postId) {
        return new Comment(userRepository.findById(commentRequest.getAuthorId())
                .orElseThrow(() -> new RuntimeException(String.format("couldnt find user with Id %d", commentRequest.getAuthorId())))
                , commentRequest.getComment(), postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException(String.format("couldnt find post by Id %d", postId)))
                , commentRequest.getRate());
    }

    public CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getId(), comment.getComment(), comment.getRate(), comment.getPost().getId());
    }
}
