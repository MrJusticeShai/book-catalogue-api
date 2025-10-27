package com.payu.assessment.bookcatalogue.service;

import com.payu.assessment.bookcatalogue.dto.BookRequest;
import com.payu.assessment.bookcatalogue.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookByIsbn(String isbn);

    boolean deleteBookByIsbn(String isbn);

    Book addBook(BookRequest bookRequest);

    Book updateBookByIsbn(String isbn, BookRequest bookRequest);
}
