package com.payu.assessment.bookcatalogue.repository;

import com.payu.assessment.bookcatalogue.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
