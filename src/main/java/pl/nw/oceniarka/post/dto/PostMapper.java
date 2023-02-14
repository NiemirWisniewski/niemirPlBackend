package pl.nw.oceniarka.post.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.nw.oceniarka.exception.userException.UserExceptionSupplier;
import pl.nw.oceniarka.post.domain.Post;
import pl.nw.oceniarka.post.dto.request.PostRequest;
import pl.nw.oceniarka.post.dto.response.PostResponse;
import pl.nw.oceniarka.user.domain.CurrentUser;
import pl.nw.oceniarka.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    public Post toPostWithoutImage(PostRequest postRequest) {
        return new Post(userRepository.findByUsername(postRequest.getAuthor())
                .orElseThrow(UserExceptionSupplier.userNotFound(postRequest.getAuthor()))
                , postRequest.getContent());
    }

    public Post toPostWithImage(PostRequest postRequest) {
        return new Post(userRepository.findByUsername(postRequest.getAuthor())
                .orElseThrow(UserExceptionSupplier.userNotFound(postRequest.getAuthor()))
                , postRequest.getContent(), postRequest.getImageUrl());
    }

    public PostResponse toPostResponseWithoutImage(Post post) {
        return new PostResponse(post.getId(), post.getUser().getUsername(), post.getContent(), post.getDateAdded());
    }

    public PostResponse toPostResponseWithImage(Post post){
        return new PostResponse(post.getId(), post.getUser().getUsername(), post.getContent(), post.getDateAdded()
        , post.getImageUrl());
    }

}