package com.payu.assessment.bookcatalogue.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.payu.assessment.bookcatalogue.dto.BookRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(columnNames = "isbn")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String isbn;

    private LocalDate publishDate;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private BookType bookType;

    public Book() {
    }

    public Book(String name, String isbn, LocalDate publishDate, BigDecimal price, BookType bookType) {
        this.name = name;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.price = price;
        this.bookType = bookType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
}
