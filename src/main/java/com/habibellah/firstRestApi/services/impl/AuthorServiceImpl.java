package com.habibellah.firstRestApi.services.impl;


import com.habibellah.firstRestApi.domain.entities.AuthorEntity;
import com.habibellah.firstRestApi.repositories.AuthorRepository;
import com.habibellah.firstRestApi.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }


    @Override
    public Page<AuthorEntity> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity authorPartialUpdate(Long id, AuthorEntity authorEntity) {
      authorEntity.setId(id);
      return authorRepository.findById(id).map(existedAuthor->{
          Optional.ofNullable(authorEntity.getName()).ifPresent(existedAuthor::setName);
          Optional.ofNullable(authorEntity.getAge()).ifPresent(existedAuthor::setAge);
          return authorRepository.save(existedAuthor);
      }).orElseThrow(()->new RuntimeException("author does not exist"));
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
