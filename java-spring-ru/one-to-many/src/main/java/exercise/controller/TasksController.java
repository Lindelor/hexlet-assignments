package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.mapper.UserMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(path = "")
    public ResponseEntity<List<TaskDTO>> index() {
        var result = taskRepository.findAll()
                .stream()
                .map(t -> taskMapper.map(t))
                .toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskDTO> show(@PathVariable Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        var taskDTO = taskMapper.map(task);

        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskCreateDTO taskCreateDTO) {
        var task = taskMapper.map(taskCreateDTO);
        var user = userRepository.findById(task.getAssignee().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.addTask(task);
        userRepository.save(user);

        var result = taskMapper.map(task);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO taskUpdateDTO) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        var user = userRepository.findById(task.getAssignee().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.removeTask(task);

        var newUser = userRepository.findById(taskUpdateDTO.getAssigneeId())
                        .orElseThrow(() -> new ResourceNotFoundException("New user not found"));

        taskMapper.update(taskUpdateDTO, task);

        user.removeTask(task);
        newUser.addTask(task);

        taskRepository.save(task);
        userRepository.save(user);
        userRepository.save(newUser);

        return new ResponseEntity<>(taskMapper.map(task), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
