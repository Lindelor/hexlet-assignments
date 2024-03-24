package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

@RestController
@RequestMapping(path = "/posts")
public class PostsController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    @GetMapping(path = "")
    public ResponseEntity<List<PostDTO>> index() {
        var result = postRepository.findAll()
                .stream()
                .map(this::toPostDTO)
                .toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDTO> show(@PathVariable Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %d not found", id)));

        return new ResponseEntity<>(toPostDTO(post), HttpStatus.OK);
    }

    private PostDTO toPostDTO(Post post) {
        var dto = new PostDTO();

        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());

        var resultCommentDTOList = commentRepository.findByPostId(post.getId())
                .stream()
                .map(this::toCommentDTO)
                .toList();

        dto.setComments(resultCommentDTOList);

        return dto;
    }

    private CommentDTO toCommentDTO(Comment comment) {
        var dto = new CommentDTO();

        dto.setBody(comment.getBody());
        dto.setId(comment.getId());

        return dto;
    }
}
