package com.habibellah.firstRestApi.contorller;


import com.habibellah.firstRestApi.domain.dto.AuthorDto;
import com.habibellah.firstRestApi.domain.entities.AuthorEntity;
import com.habibellah.firstRestApi.mappers.Mapper;
import com.habibellah.firstRestApi.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    private final Mapper<AuthorEntity, AuthorDto> authorMapper;


    @Autowired
    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public Page<AuthorDto> getAuthors(Pageable pageable) {
        return authorService.findAll(pageable)
                .map(authorMapper::mapTo);
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorBy(@PathVariable("id") Long id) {
        Optional<AuthorEntity> authorFounded = authorService.findOne(id);
        return authorFounded.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id,
                                                  @RequestBody AuthorDto authorDto) {
        if (!authorService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        AuthorEntity authorUpdated = authorService.save(authorMapper.mapFrom(authorDto));
        AuthorDto author = authorMapper.mapTo(authorUpdated);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> partialAuthorUpdate(@PathVariable("id") Long id,
                                                         @RequestBody AuthorDto authorDto) {
        if (!authorService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity authorPartialUpdated = authorService.authorPartialUpdate(id, authorEntity);
        return new ResponseEntity<>(
                authorMapper.mapTo(authorPartialUpdated),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity deleteAuthor(
            @PathVariable("id") Long id
    ){
        if (!authorService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
