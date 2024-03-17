package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/posts")
    public List<Post> index(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit) {
        return posts.stream().skip(((long) page - 1) * limit).limit(limit).toList();
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> show(@PathVariable String id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }

    @PutMapping("/posts/{id}")
    public Post update(@PathVariable String id, @RequestBody Post data) {
        var optPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();

        if (optPost.isPresent()) {
            var post = optPost.get();

            post.setId(data.getId());
            post.setBody(data.getBody());
            post.setTitle(data.getTitle());
        }
        return data;
    }

}
