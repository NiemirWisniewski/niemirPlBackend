package niemir.comment.service;

import lombok.RequiredArgsConstructor;
import niemir.comment.domain.Comment;
import niemir.comment.dto.request.CommentRequest;
import niemir.comment.dto.response.CommentResponse;
import niemir.exceptions.commentException.CommentExceptionSupplier;
import niemir.exceptions.userException.UserExceptionSupplier;
import niemir.user.domain.User;
import niemir.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import niemir.comment.dto.CommentMapper;
import niemir.comment.repository.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentResponse saveComment(CommentRequest commentRequest, Long postId) {
        Comment comment = commentMapper.toComment(commentRequest, postId);
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    public CommentResponse findById(Long id) {
        return commentMapper.toCommentResponse(commentRepository.findById(id)
               .orElseThrow(CommentExceptionSupplier.commentNotFound(id)));
    }

    public List<CommentResponse> findAllAuthorsComments(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(UserExceptionSupplier.userNotFound(username));
        List<Comment> commentList = commentRepository.findAllByAuthor(user.getId());
        return commentList.stream().map(commentMapper::toCommentResponse).toList();
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentExceptionSupplier.commentNotFound(id));
        commentRepository.delete(comment);
    }

    public CommentResponse update(Long postId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(postId)
                .orElseThrow(CommentExceptionSupplier.commentNotFound(postId));
        comment.setComment(commentRequest.getComment());
        comment.setRate(commentRequest.getRate());
        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }
}