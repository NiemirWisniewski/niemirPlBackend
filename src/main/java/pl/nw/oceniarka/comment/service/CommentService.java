package pl.nw.oceniarka.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nw.oceniarka.comment.domain.Comment;
import pl.nw.oceniarka.comment.dto.CommentMapper;
import pl.nw.oceniarka.comment.dto.request.CommentRequest;
import pl.nw.oceniarka.comment.dto.response.CommentResponse;
import pl.nw.oceniarka.comment.repository.CommentRepository;
import pl.nw.oceniarka.exception.commentException.CommentExceptionSupplier;
import pl.nw.oceniarka.exception.userException.UserExceptionSupplier;
import pl.nw.oceniarka.post.repository.PostRepository;
import pl.nw.oceniarka.user.domain.User;
import pl.nw.oceniarka.user.repository.UserRepository;

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