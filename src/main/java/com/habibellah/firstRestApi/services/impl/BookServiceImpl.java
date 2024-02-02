package com.habibellah.firstRestApi.services.impl;

import com.habibellah.firstRestApi.domain.entities.BookEntity;
import com.habibellah.firstRestApi.repositories.BookRepository;
import com.habibellah.firstRestApi.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createUpdateBook(BookEntity bookEntity, String isbn) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findById(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExist(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
       return bookRepository.findById(isbn).map(existingBook ->{
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
          return   bookRepository.save(existingBook);
        }).orElseThrow(()-> new RuntimeException("book does not exist"));
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
