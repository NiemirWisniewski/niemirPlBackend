package niemir.post.service;

import niemir.exceptions.postException.ProcessException;
import niemir.exceptions.userException.UserNotFoundException;
import niemir.post.domain.Post;
import niemir.post.dto.PostMapper;
import niemir.post.dto.request.PostRequest;
import niemir.post.dto.response.PostResponse;
import niemir.post.repository.PostRepository;
import niemir.user.domain.CurrentUser;
import niemir.user.domain.User;
import niemir.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

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

    public Page<PostResponse> findAll(int offset, int pageSize) {

        Page<Post> postsPage = postRepository.findAll(PageRequest.of(offset, pageSize)
                .withSort(Sort.by("dateAdded").descending()));
        return postsPage.map(postMapper::toPostResponseWithImage);

    }

    private Supplier<? extends RuntimeException> userNotFound(Long id){
        return userNotFound(id);
    }
}
