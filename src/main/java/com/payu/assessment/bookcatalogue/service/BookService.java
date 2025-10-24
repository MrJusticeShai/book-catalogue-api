package com.payu.assessment.bookcatalogue.service;

import com.payu.assessment.bookcatalogue.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book addBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);
}
