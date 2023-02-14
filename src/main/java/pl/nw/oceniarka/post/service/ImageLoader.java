package pl.nw.oceniarka.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.nw.oceniarka.post.domain.Post;
import pl.nw.oceniarka.post.repository.PostRepository;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class ImageLoader {

    private final PostRepository postRepository;

    public InputStream loadImage(Long id) throws IOException{
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cant find such post in repository"));
        return new BufferedInputStream(
                new FileInputStream(post.getImageUrl()));
    }

}
