package com.habibellah.firstRestApi.contorller;


import com.habibellah.firstRestApi.domain.dto.BookDto;
import com.habibellah.firstRestApi.domain.entities.BookEntity;
import com.habibellah.firstRestApi.mappers.Mapper;
import com.habibellah.firstRestApi.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class BookController {

     private final BookService bookService;

     private final Mapper<BookEntity,BookDto> mapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn,@RequestBody BookDto bookDto){
        BookEntity bookEntityResult = bookService.createUpdateBook(mapper.mapFrom(bookDto),isbn);
        if(!bookService.isExist(isbn)){
            return new ResponseEntity<>(mapper.mapTo(bookEntityResult), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(mapper.mapTo(bookEntityResult), HttpStatus.OK);
        }

    }


    @GetMapping("/books")
    public Page<BookDto> getAllBooks(Pageable pageable){
        return bookService.findAll(pageable)
                .map(mapper::mapTo);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBookBy(@PathVariable("isbn") String isbn){
        Optional<BookEntity> bookFounded = bookService.findById(isbn);
       return bookFounded.map(bookEntity -> {
            BookDto book = mapper.mapTo(bookEntity);
            return new ResponseEntity<>(book,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ){
        if(!bookService.isExist(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = mapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn,bookEntity);
        return new ResponseEntity<>(
                mapper.mapTo(updatedBookEntity),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity deleteBook(
            @PathVariable("isbn") String isbn
    ){
        if(!bookService.isExist(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
