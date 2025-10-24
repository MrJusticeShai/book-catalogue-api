package com.payu.assessment.bookcatalogue.dto;

import com.payu.assessment.bookcatalogue.model.BookType;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BookRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String isbn;

    @NotNull
    private LocalDate publishDate;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Schema(description = "Type of book", allowableValues = {"HARDCOVER", "SOFTCOVER", "EBOOK", "AUDIOBOOK"})
    private BookType bookType;

    public BookRequest(String name, String isbn, LocalDate publishDate, BigDecimal price, BookType bookType) {
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
