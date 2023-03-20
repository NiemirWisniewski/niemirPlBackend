package niemir.post.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import niemir.exceptions.postException.ProcessException;
import niemir.post.dto.request.PostRequest;
import niemir.post.dto.response.PostResponse;
import niemir.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/posts")
@Api(tags = "Post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @ApiOperation("Show posts")
    public ResponseEntity<Page<PostResponse>> showPosts(@RequestParam int offset) {
        Page<PostResponse> postResponsePage = postService.findAll(offset,10);
        return ResponseEntity.status(HttpStatus.OK).body(postResponsePage);
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