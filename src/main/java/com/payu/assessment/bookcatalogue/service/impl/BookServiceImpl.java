package com.payu.assessment.bookcatalogue.service.impl;

import com.payu.assessment.bookcatalogue.dto.BookRequest;
import com.payu.assessment.bookcatalogue.exception.BookAlreadyExistsException;
import com.payu.assessment.bookcatalogue.exception.BookNotFoundException;
import com.payu.assessment.bookcatalogue.model.Book;
import com.payu.assessment.bookcatalogue.repository.BookRepository;
import com.payu.assessment.bookcatalogue.service.BookService;
import com.payu.assessment.bookcatalogue.util.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
    }

    @Override
    public Book addBook(BookRequest bookRequest) {
        if (bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            throw new BookAlreadyExistsException(
                    String.format("Cannot add book. A book with ISBN '%s' already exists.", bookRequest.getIsbn())
            );
        }

        Book book = BookMapper.fromRequest(bookRequest);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, BookRequest request) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
        boolean updated = BookMapper.updateFromRequest(existing, request);
        if (updated) {
            return bookRepository.save(existing);
        }
        return existing;
    }

    @Override
    public void deleteBook(Long id) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
        bookRepository.delete(existing);
    }
}
