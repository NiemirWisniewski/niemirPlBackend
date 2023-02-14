package pl.nw.oceniarka.post.api;

import lombok.RequiredArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nw.oceniarka.post.service.ImageLoader;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageLoader imageLoader;

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException{

        InputStream inputStream = imageLoader.loadImage(id);
        return new ResponseEntity<>(IOUtils.toByteArray(inputStream), HttpStatus.OK);
    }
}
