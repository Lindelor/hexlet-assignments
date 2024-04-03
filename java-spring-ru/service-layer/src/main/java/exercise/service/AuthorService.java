package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAll() {
        var result = authorRepository.findAll()
                .stream()
                .map(authorMapper::map)
                .toList();

        return result;
    }

    public AuthorDTO findById(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        return authorMapper.map(author);
    }

    public AuthorDTO create(AuthorCreateDTO authorCreateDTO) {
        var author = authorMapper.map(authorCreateDTO);

        authorRepository.save(author);

        return authorMapper.map(author);
    }

    public void remove(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorDTO update(Long id, AuthorUpdateDTO authorUpdateDTO) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        authorMapper.update(authorUpdateDTO, author);

        authorRepository.save(author);

        return authorMapper.map(author);
    }
}
