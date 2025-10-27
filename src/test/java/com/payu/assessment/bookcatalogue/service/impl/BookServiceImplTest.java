package com.payu.assessment.bookcatalogue.service.impl;

import com.payu.assessment.bookcatalogue.dto.BookRequest;
import com.payu.assessment.bookcatalogue.exception.BookAlreadyExistsException;
import com.payu.assessment.bookcatalogue.exception.BookNotFoundException;
import com.payu.assessment.bookcatalogue.model.Book;
import com.payu.assessment.bookcatalogue.model.BookType;
import com.payu.assessment.bookcatalogue.repository.BookRepository;
import com.payu.assessment.bookcatalogue.util.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookRepository bookRepository;
    private BookServiceImpl bookService;
    private Book book1;
    private Book book2;
    private BookRequest request;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookServiceImpl(bookRepository);

        book1 = new Book("Book1", "111", testLocalDate, 149.99, BookType.HARDCOVER);
        book2 = new Book("Book2", "222", testLocalDate, 189.99, BookType.SOFTCOVER);

        request = new BookRequest("Book1", "111", testLocalDate, 149.99, BookType.HARDCOVER);
    }

    private final LocalDate testLocalDate = LocalDate.of(2025, 10, 24);

    // ----------------- getAllBooks -----------------
    @Test
    void getAllBooks_returnsAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    // ----------------- getBookByIsbn -----------------
    @Test
    void getBookByIsbn_existingIsbn_returnsBook() {
        when(bookRepository.findByIsbn("111")).thenReturn(Optional.of(book1));

        Book result = bookService.getBookByIsbn("111");

        assertEquals("Book1", result.getName());
    }

    @Test
    void getBookByIsbn_nonExistingIsbn_throwsException() {
        when(bookRepository.findByIsbn("111")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookByIsbn("111"));
    }


    // ----------------- addBook -----------------
    @Test
    void addBook_uniqueIsbn_savesBook() {
        when(bookRepository.existsByIsbn("111")).thenReturn(false);

        Book savedBook = BookMapper.fromRequest(request);
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        Book result = bookService.addBook(request);

        assertEquals("Book1", result.getName());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void addBook_existingIsbn_throwsException() {
        when(bookRepository.existsByIsbn("111")).thenReturn(true);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.addBook(request));
        verify(bookRepository, never()).save(any(Book.class));
    }

    // ----------------- updateBook -----------------
    @Test
    void updateBook_existingBook_updatesFields() {
        Book existing = new Book("Old Book", "111", testLocalDate, 149.99, BookType.HARDCOVER);
        when(bookRepository.findByIsbn("111")).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookRequest request = new BookRequest("New Book", "111", testLocalDate.plusDays(1), 189.99, BookType.SOFTCOVER);

        Book result = bookService.updateBookByIsbn("111", request);

        assertEquals("New Book", result.getName());
        assertEquals("111", result.getIsbn());
        assertEquals(189.99, result.getPrice());
        assertEquals(BookType.SOFTCOVER, result.getBookType());
    }

    @Test
    void updateBook_nonExistingBook_throwsException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.updateBookByIsbn("111", request));
    }

    @Test
    void updateBook_noChanges_doesNotSave() {
        Book existing = new Book("Same Book", "111", testLocalDate, 149.99, BookType.HARDCOVER);
        when(bookRepository.findByIsbn("111")).thenReturn(Optional.of(existing));

        BookRequest request = new BookRequest("Same Book", "111", testLocalDate, 149.99, BookType.HARDCOVER);

        bookService.updateBookByIsbn("111", request);

        verify(bookRepository, never()).save(any(Book.class));
    }


    // ----------------- deleteBook -----------------
    @Test
    void deleteBook_existingBook_deletesBook() {
        Book existing = new Book("Book1", "111", testLocalDate, 149.99, BookType.HARDCOVER);
        when(bookRepository.findByIsbn("111")).thenReturn(Optional.of(existing));
        when(bookRepository.existsByIsbn("111")).thenReturn(true);

        bookService.deleteBookByIsbn("111");

        verify(bookRepository, times(1)).deleteByIsbn("111");
    }

    @Test
    void deleteBook_nonExistingBook_throwsException() {
        when(bookRepository.findByIsbn("111")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookByIsbn("111"));
    }
}

