package com.backend.restapi.spring_boot_library.repository;

import com.backend.restapi.spring_boot_library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
