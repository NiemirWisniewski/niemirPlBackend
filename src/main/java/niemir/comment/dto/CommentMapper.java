package niemir.comment.dto;

import lombok.RequiredArgsConstructor;
import niemir.comment.domain.Comment;
import niemir.comment.dto.request.CommentRequest;
import niemir.comment.dto.response.CommentResponse;
import niemir.post.repository.PostRepository;
import niemir.user.repository.UserRepository;
import org.springframework.stereotype.Component;

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
