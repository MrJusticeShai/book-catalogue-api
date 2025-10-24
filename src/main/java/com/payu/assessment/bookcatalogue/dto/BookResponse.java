package com.payu.assessment.bookcatalogue.dto;

import com.payu.assessment.bookcatalogue.model.BookType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookResponse {

    private String name;
    private String isbn;

    private LocalDate publishDate;

    private BigDecimal price;
    private BookType bookType;

    public BookResponse(String name, String isbn, LocalDate publishDate, BigDecimal price, BookType bookType) {
        this.name = name;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.price = price;
        this.bookType = bookType;
    }

    public String getName() {
        return name;
    }
    public String getIsbn() {
        return isbn;
    }
    public LocalDate getPublishDate() {
        return publishDate;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public BookType getBookType() {
        return bookType;
    }
}
