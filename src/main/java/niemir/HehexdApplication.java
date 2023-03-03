package niemir;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import niemir.comment.repository.CommentRepository;
import niemir.post.repository.PostRepository;
import niemir.user.repository.UserRepository;

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