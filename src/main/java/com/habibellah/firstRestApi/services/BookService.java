package com.habibellah.firstRestApi.services;

import com.habibellah.firstRestApi.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface BookService {

    BookEntity createUpdateBook(BookEntity bookEntity, String isbn);

    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findById(String isbn);

    boolean isExist(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

    void deleteBook(String isbn);
}
