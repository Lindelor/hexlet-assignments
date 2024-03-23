package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping(path = "comments")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public ResponseEntity<List<Comment>> index() {
        return new ResponseEntity<>(commentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Comment> show(@PathVariable Long id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Comment with id %d not found", id)));

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<Comment> create(@RequestBody Comment commentData) {
        return new ResponseEntity<>(commentRepository.save(commentData), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Comment> update(@PathVariable Long id, @RequestBody Comment commentData) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Comment with id %d not found", id)));

        comment.setBody(commentData.getBody());

        return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public void remove(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
