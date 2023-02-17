package pl.nw.oceniarka.post.api;

import lombok.RequiredArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nw.oceniarka.post.service.FileLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileLoader fileLoader;

    @GetMapping(value = "/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException{

        InputStream inputStream = fileLoader.loadImage(id);
        return new ResponseEntity<>(IOUtils.toByteArray(inputStream), HttpStatus.OK);
    }

    /*@GetMapping("/cv")
    public ResponseEntity<byte[]> getCV() throws IOException{

        InputStream inputStream = fileLoader.loadCV();
        return new ResponseEntity<>(IOUtils.toByteArray(inputStream), HttpStatus.OK);
    }*/

    @GetMapping("/cv")
    public ResponseEntity<Resource> downloadCV() throws IOException{
        File newFile = fileLoader.loadCV();
        Resource resource = new UrlResource(newFile.toURI());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", "Niemir_Wisniewski_CV");
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(resource.getFile().toPath())))
                .headers(httpHeaders).body(resource);
    }
}
