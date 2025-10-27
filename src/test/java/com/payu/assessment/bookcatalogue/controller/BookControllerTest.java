package com.payu.assessment.bookcatalogue.controller;

import com.payu.assessment.bookcatalogue.dto.BookRequest;
import com.payu.assessment.bookcatalogue.dto.BookResponse;
import com.payu.assessment.bookcatalogue.exception.BookAlreadyExistsException;
import com.payu.assessment.bookcatalogue.exception.BookNotFoundException;
import com.payu.assessment.bookcatalogue.model.Book;
import com.payu.assessment.bookcatalogue.model.BookType;
import com.payu.assessment.bookcatalogue.service.BookService;
import com.payu.assessment.bookcatalogue.util.BookMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final String addBookStringBody = "{"
            + "\"name\":\"Book1\","
            + "\"isbn\":\"111\","
            + "\"publishDate\":\"24/10/2025\","
            + "\"price\":149.99,"
            + "\"bookType\":\"HARDCOVER\""
            + "}";

    private final String updateBookstringBody = "{"
            + "\"name\":\"Updated\","
            + "\"isbn\":\"111\","
            + "\"publishDate\":\"24/10/2025\","
            + "\"price\":20,"
            + "\"bookType\":\"SOFTCOVER\""
            + "}";

    private final LocalDate testLocalDate = LocalDate.of(2025, 10, 24);

    // ------------------ GET /api/books ------------------
    @Test
    void getAllBooks_returnsListOfBookResponses() throws Exception {
        Book book1 = new Book("Book1", "111", testLocalDate, 149.99, BookType.HARDCOVER);
        Book book2 = new Book("Book2", "222", testLocalDate, 189.99, BookType.SOFTCOVER);
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        List<BookResponse> responses = Arrays.asList(
                BookMapper.toResponse(book1),
                BookMapper.toResponse(book2)
        );

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(responses.size()))
                .andExpect(jsonPath("$[0].name").value("Book1"));
    }


    // ------------------ GET /api/books/isbn/{isbn} ------------------
    @Test
    void getBookByIsbn_existingIsbn_returnsBookResponse() throws Exception {
        Book book = new Book("Book1", "111", testLocalDate, 149.99, BookType.HARDCOVER);
        when(bookService.getBookByIsbn("111")).thenReturn(book);

        mockMvc.perform(get("/api/books/isbn/111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book1"))
                .andExpect(jsonPath("$.isbn").value("111"));
    }

    @Test
    void getBookByIsbn_nonExistingIsbn_returns404() throws Exception {
        when(bookService.getBookByIsbn("111")).thenThrow(new BookNotFoundException("Book not found"));

        mockMvc.perform(get("/api/books/isbn/111"))
                .andExpect(status().isNotFound());
    }


    // ------------------ POST /api/books ------------------
    @Test
    void addBook_validRequest_returnsCreatedBookResponse() throws Exception {
        Book savedBook = new Book("Book1", "111", testLocalDate, 149.99, BookType.HARDCOVER);
        when(bookService.addBook(any(BookRequest.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addBookStringBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book1"))
                .andExpect(jsonPath("$.isbn").value("111"))
                .andExpect(jsonPath("$.publishDate").value("24/10/2025"))
                .andExpect(jsonPath("$.price").value(149.99))
                .andExpect(jsonPath("$.bookType").value("HARDCOVER"));
    }

    @Test
    void addBook_existingIsbn_returnsConflict() throws Exception {
        when(bookService.addBook(any(BookRequest.class)))
                .thenThrow(new BookAlreadyExistsException("Book with ISBN already exists"));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addBookStringBody))
                .andExpect(status().isConflict());
    }

    // ------------------ PUT /api/books/isbn/{isbn} ------------------
    @Test
    void updateBook_existingIsbn_returnsUpdatedBookResponse() throws Exception {
        Book updated = new Book("Updated", "111", testLocalDate, 189.99, BookType.SOFTCOVER);

        when(bookService.updateBookByIsbn(eq("111"), any(BookRequest.class))).thenReturn(updated);

        mockMvc.perform(put("/api/books/isbn/111")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBookstringBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void updateBook_nonExistingIsbn_returns404() throws Exception {
        // Mock service to throw BookNotFoundException
        when(bookService.updateBookByIsbn(eq("111"), any(BookRequest.class)))
                .thenThrow(new BookNotFoundException("Book not found"));

        mockMvc.perform(put("/api/books/isbn/111")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBookstringBody)) // e.g., {"name":"Test","isbn":"111","price":100.0,"publishDate":"24/10/2025","bookType":"HARDCOVER"}
                .andExpect(status().isNotFound());
    }

    // ------------------ DELETE /api/books/isbn/{isbn} ------------------
    @Test
    void deleteBook_existingIsbn_returnsNoContent() throws Exception {
        // No exception thrown, service returns true
        when(bookService.deleteBookByIsbn("111")).thenReturn(true);

        mockMvc.perform(delete("/api/books/isbn/111"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBookByIsbn("111");
    }

    @Test
    void deleteBook_nonExistingIsbn_returns404() throws Exception {
        // Mock the service to throw exception when ISBN not found
        doThrow(new BookNotFoundException("Book not found"))
                .when(bookService).deleteBookByIsbn("111");

        mockMvc.perform(delete("/api/books/isbn/111")) // Must match controller path
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).deleteBookByIsbn("111");
    }
}
