package pl.nw.oceniarka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.nw.oceniarka.comment.domain.Comment;
import pl.nw.oceniarka.comment.repository.CommentRepository;
import pl.nw.oceniarka.post.domain.Post;
import pl.nw.oceniarka.post.repository.PostRepository;
import pl.nw.oceniarka.user.domain.User;
import pl.nw.oceniarka.user.domain.role.Role;
import pl.nw.oceniarka.user.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@SpringBootApplication
public class HehexdApplication {

	public static void main(String[] args) {
		SpringApplication.run(HehexdApplication.class, args);
	}
	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentRepository commentRepository;


	/*
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder encoder, PostRepository postRepository, CommentRepository commentRepository){
	return args -> {


		userRepository.save(new User("user", encoder.encode("user"), Role.USER));
		userRepository.save(new User("mod", encoder.encode("mod"), Role.MOD));
		userRepository.save(new User("aron", encoder.encode("admin"), Role.ADMIN));

		postRepository.save(new Post(userRepository.findById(1L).orElseThrow() , "pierwszy post usera"));
		postRepository.save(new Post(userRepository.findById(2L).orElseThrow(), "pierwszy post moda"));
		postRepository.save(new Post(userRepository.findById(3L).orElseThrow(), "pierwszy post admina"));

		commentRepository.save(new Comment(userRepository.findById(1L).orElseThrow(), "pierwszy komentarz usera", postRepository.findById(1L).orElseThrow(), 9.5));
		commentRepository.save(new Comment(userRepository.findById(2L).orElseThrow(), "pierwszy komentarz moda", postRepository.findById(2L).orElseThrow(), 6.5));
		commentRepository.save(new Comment(userRepository.findById(3L).orElseThrow(), "pierwszy komentarz admina", postRepository.findById(3L).orElseThrow(), 4.5));
	}; }

	//EventListener
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent){
		postRepository.save(new Post(userRepository.findById(1L).orElseThrow() , "ApplicationReadyEvent post"));
	}*/
}