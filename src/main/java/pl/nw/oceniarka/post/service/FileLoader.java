package pl.nw.oceniarka.post.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.nw.oceniarka.post.domain.Post;
import pl.nw.oceniarka.post.repository.PostRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileLoader {

    private final PostRepository postRepository;
    private final String imagesPath;
    public FileLoader(PostRepository postRepository, @Value("${imagesPath}") String imagesPath) {
        this.postRepository = postRepository;
        this.imagesPath = imagesPath;
    }

    public InputStream loadImage(Long id) throws IOException{
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cant find such post in repository"));
        return new BufferedInputStream(
                new FileInputStream(post.getImageUrl()));
    }

    public File loadCV() throws IOException{

        File dir = new File(imagesPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir.getAbsolutePath() + "/CV/Niemir_Wisniewski_CV.pdf");
        if(!newFile.exists()) {
            throw new FileNotFoundException("pdf file Niemir_Wisniewski_CV wasnt found in designated path");
        }
        return newFile;
        //return new BufferedInputStream(
        //        new FileInputStream(newFile));
        //"C:/Users/niemi/Desktop/niemirPl/niemirPlBackend-master/#form/CV/Niemir_Wisniewski_CV.pdf"
    }

}
