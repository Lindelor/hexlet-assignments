package exercise.controller.users;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

@RestController
@RequestMapping("/api")
public class PostsController {
    private List<Post> posts = Data.getPosts();
    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<Post>> show(@PathVariable int id) {
        var result = posts.stream().filter(p -> p.getUserId() == id).toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> create(@PathVariable int id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
}
