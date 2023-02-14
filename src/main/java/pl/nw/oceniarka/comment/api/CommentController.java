package pl.nw.oceniarka.comment.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nw.oceniarka.comment.dto.request.CommentRequest;
import pl.nw.oceniarka.comment.dto.response.CommentResponse;
import pl.nw.oceniarka.comment.service.CommentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Api(tags = "Comment")
@RequiredArgsConstructor
public class CommentController{

    private final CommentService commentService;

    @PostMapping("/{postId}")
    @ApiOperation("Post Comment")
    public ResponseEntity<CommentResponse> postComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest){
        CommentResponse commentResponse = commentService.saveComment(commentRequest, postId);
        URI uri = URI.create("/api/comments/" + commentResponse.getId());
        return ResponseEntity.created(uri).body(commentResponse);
    }


    //Nie działa, coś z baza danych, pewnie jakis problem z baza danych, Parameter value [user] did not match expected type [pl.nw.oceniarka.user.domain.User (n/a)], Parameter value [1] did not match expected type [pl.nw.oceniarka.user.domain.User (n/a)]
    @GetMapping("/username/{username}")
    @ApiOperation("Show all comments from this author")
    public ResponseEntity<List<CommentResponse>> showAllComments(@PathVariable String username){
        List<CommentResponse> commentResponseList = commentService.findAllAuthorsComments(username);
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseList);
    }



    @GetMapping("/{id}")
    @ApiOperation("Find comment")
    public ResponseEntity<CommentResponse> findComment(@PathVariable Long id){
        CommentResponse commentResponse = commentService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentResponse);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete comment")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{postId}")
    @ApiOperation("Update comment")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest){
        CommentResponse commentResponse = commentService.update(postId, commentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(commentResponse);
    }
}