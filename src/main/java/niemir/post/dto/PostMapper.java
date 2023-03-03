package niemir.post.dto;

import lombok.RequiredArgsConstructor;
import niemir.exceptions.userException.UserExceptionSupplier;
import org.springframework.stereotype.Component;
import niemir.post.domain.Post;
import niemir.post.dto.request.PostRequest;
import niemir.post.dto.response.PostResponse;
import niemir.user.domain.CurrentUser;
import niemir.user.repository.UserRepository;

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