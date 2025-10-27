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

import javax.transaction.Transactional;
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
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book with ISBN " + isbn + " not found"));
    }

    @Override
    @Transactional
    public boolean deleteBookByIsbn(String isbn) {
        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
        }

        bookRepository.deleteByIsbn(isbn);
        return true;
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
    public Book updateBookByIsbn(String isbn, BookRequest bookRequest) {
        Book existing = getBookByIsbn(isbn);
        boolean updated = BookMapper.updateFromRequest(existing, bookRequest);
        if (updated) {
            return bookRepository.save(existing);
        }
        return existing;
    }
}
