package com.payu.assessment.bookcatalogue.controller;

import com.payu.assessment.bookcatalogue.dto.BookRequest;
import com.payu.assessment.bookcatalogue.dto.BookResponse;
import com.payu.assessment.bookcatalogue.exception.BookNotFoundException;
import com.payu.assessment.bookcatalogue.model.Book;
import com.payu.assessment.bookcatalogue.service.BookService;
import com.payu.assessment.bookcatalogue.util.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        if (book == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(BookMapper.toResponse(book));
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest request) {
        Book created = bookService.addBook(request);
        return ResponseEntity.ok(BookMapper.toResponse(created));
    }

    @PutMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> updateBookByIsbn(@PathVariable String isbn, @RequestBody BookRequest updatedBook) {
        try {
            Book saved = bookService.updateBookByIsbn(isbn, updatedBook);
            return ResponseEntity.ok(BookMapper.toResponse(saved));
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/isbn/{isbn}")
    public ResponseEntity<Void> deleteBookByIsbn(@PathVariable String isbn) {
        boolean deleted = bookService.deleteBookByIsbn(isbn);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

}
