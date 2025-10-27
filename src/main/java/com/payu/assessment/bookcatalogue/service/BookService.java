package com.payu.assessment.bookcatalogue.service;

import com.payu.assessment.bookcatalogue.dto.BookRequest;
import com.payu.assessment.bookcatalogue.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book getBookByIsbn(String isbn);

    Book addBook(BookRequest bookRequest);

    Book updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);
}
