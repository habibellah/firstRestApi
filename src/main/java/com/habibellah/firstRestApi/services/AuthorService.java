package com.habibellah.firstRestApi.services;

import com.habibellah.firstRestApi.domain.entities.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);

    Page<AuthorEntity> findAll(Pageable pageable);

    Optional<AuthorEntity> findOne(Long id);

    boolean isExist(Long id);

    AuthorEntity authorPartialUpdate(Long id, AuthorEntity authorEntity);

    void deleteAuthor(Long id);
}
