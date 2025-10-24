package com.payu.assessment.bookcatalogue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.payu.assessment.bookcatalogue.model.BookType;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookRequest {

    @NotBlank
    @Schema(description = "Name of the book", example = "Effective Java")
    private String name;

    @NotBlank
    @Schema(description = "ISBN of the book", example = "978-0134685991")
    private String isbn;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(type = "string", pattern = "dd/MM/yyyy", example = "31/12/2025")
    private LocalDate publishDate;

    @NotNull
    @Min(value = 0, message = "Price cannot be negative")
    @Schema(description = "Price of the book", example = "149.99")
    private Double price;

    @NotNull
    @Schema(description = "Type of book", allowableValues = {"HARDCOVER", "SOFTCOVER", "EBOOK", "AUDIOBOOK"}, example = "HARDCOVER")
    private BookType bookType;

    public BookRequest() {
    }

    public BookRequest(String name, String isbn, LocalDate publishDate, Double price, BookType bookType) {
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

    public Double getPrice() {
        return price;
    }

    public BookType getBookType() {
        return bookType;
    }
}
