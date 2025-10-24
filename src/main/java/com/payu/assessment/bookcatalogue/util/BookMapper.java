package com.payu.assessment.bookcatalogue.util;

import com.payu.assessment.bookcatalogue.dto.BookRequest;
import com.payu.assessment.bookcatalogue.dto.BookResponse;
import com.payu.assessment.bookcatalogue.model.Book;

public class BookMapper {

    public static Book fromRequest(BookRequest request) {
        return new Book(
                request.getName(),
                request.getIsbn(),
                request.getPublishDate(),
                request.getPrice(),
                request.getBookType()
        );
    }

    public static BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getName(),
                book.getIsbn(),
                book.getPublishDate(),
                book.getPrice(),
                book.getBookType()
        );
    }

    public static boolean updateFromRequest(Book existing, BookRequest request) {
        boolean changed = false;

        if (request.getName() != null && !request.getName().equals(existing.getName())) {
            existing.setName(request.getName());
            changed = true;
        }

        if (request.getPublishDate() != null && !request.getPublishDate().equals(existing.getPublishDate())) {
            existing.setPublishDate(request.getPublishDate());
            changed = true;
        }

        if (request.getPrice() != null && !request.getPrice().equals(existing.getPrice())) {
            existing.setPrice(request.getPrice());
            changed = true;
        }

        if (request.getBookType() != null && !request.getBookType().equals(existing.getBookType())) {
            existing.setBookType(request.getBookType());
            changed = true;
        }

        return changed;
    }
}
