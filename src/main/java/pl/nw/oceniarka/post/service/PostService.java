package pl.nw.oceniarka.post.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.nw.oceniarka.exception.postException.PostExceptionSupplier;
import pl.nw.oceniarka.exception.postException.ProcessException;
import pl.nw.oceniarka.exception.userException.UserNotFoundException;
import pl.nw.oceniarka.post.domain.Post;
import pl.nw.oceniarka.post.dto.PostMapper;
import pl.nw.oceniarka.post.dto.request.PostRequest;
import pl.nw.oceniarka.post.dto.response.PostResponse;
import pl.nw.oceniarka.post.repository.PostRepository;
import pl.nw.oceniarka.user.domain.CurrentUser;
import pl.nw.oceniarka.user.domain.User;
import pl.nw.oceniarka.user.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final String imagesPath;

    public PostService(PostMapper postMapper, PostRepository postRepository, UserRepository userRepository,
                       CurrentUser currentUser,@Value("${imagesPath}") String imagesPath) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.imagesPath = imagesPath;
    }

    public PostResponse createPost(PostRequest postRequest, MultipartFile file)
            throws ProcessException, IOException {

        User user = userRepository.findByUsername(postRequest.getAuthor())
                .orElseThrow(() -> new UserNotFoundException("Nie można znaleźć zalogowanego użytkownika w bazie"));
        Post post;

        if(postRequest.getImageUrl() == null) {
            post = postMapper.toPostWithoutImage(postRequest);
            postRepository.save(post);
            return postMapper.toPostResponseWithoutImage(post);
        } else {
            post = postMapper.toPostWithImage(postRequest);
            postRepository.save(post);
            storeItemImage(postRequest, file, post);
            postRepository.save(post);
            return postMapper.toPostResponseWithImage(post);
        }
    }

    private void storeItemImage(PostRequest postRequest, MultipartFile file, Post post) throws IOException {
        if (file != null) {
            String image = postRequest.getImageUrl();
            post.setImageUrl(image);
            saveFile(file, post);
        }
    }

    private void saveFile(MultipartFile file, Post post) throws IOException {
        File dir = new File(imagesPath + "niemirPl-" + post.getId() + "/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir.getAbsolutePath() + "/" + file.getOriginalFilename());
        file.transferTo(newFile);
        post.setImageUrl(newFile.getAbsolutePath());
    }

    public List<PostResponse> findAll() {
        Sort sort = Sort.by("dateAdded").descending();

        List<Post> postsList = postRepository.findAll(sort);
        return postsList.stream().map(postMapper::toPostResponseWithImage).collect(Collectors.toList());
    }

    public PostResponse findById(Long id){
        return postMapper.toPostResponseWithImage(postRepository.findById(id).orElseThrow(PostExceptionSupplier.postNotFound(id)));
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostExceptionSupplier.postNotFound(id));
        postRepository.delete(post);
    }


    public PostResponse update(Long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(PostExceptionSupplier.postNotFound(id));
        post.setContent(postRequest.getContent());
        post.setUser(userRepository.findById(currentUser.getId())
                .orElseThrow(PostExceptionSupplier.postNotFound(id)));
        postRepository.save(post);
        return postMapper.toPostResponseWithImage(post);
    }

    private Supplier<? extends RuntimeException> userNotFound(Long id){
        return userNotFound(id);
    }
}
