package niemir.post.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import niemir.exceptions.postException.ProcessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import niemir.post.dto.request.PostRequest;
import niemir.post.dto.response.PostResponse;
import niemir.post.service.PostService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Api(tags = "Post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @ApiOperation("Show all posts")
    public ResponseEntity<List<PostResponse>> showAllPosts() {
        List<PostResponse> postResponseList = postService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(postResponseList);
    }

    @PostMapping
    @ApiOperation("Create post")
    public ResponseEntity<Void> createPost
            (@RequestPart PostRequest postRequest, @RequestPart(required = false)
             MultipartFile file) throws ProcessException, IOException {

        PostResponse postResponse = postService.createPost(postRequest, file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}