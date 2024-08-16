package com.backend.restapi.spring_boot_library.controller;

import com.backend.restapi.spring_boot_library.entity.Book;
import com.backend.restapi.spring_boot_library.service.BookService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService =  bookService;
    }

    @GetMapping("/secure/currentLoans/count")
    public int currentLoan(){
        String userEmail =  "testuser@email.com";
        return bookService.currentLoanCount(userEmail);
    }

    @GetMapping("/secure/isCheckedOut/byUser")
    public Boolean checkoutBookByUser(@RequestParam Long bookId){
        String userEmail = "testuser@email.com";
        return bookService.checkoutBookByUser(userEmail,bookId);
    }

    @PostMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId)throws Exception{
        String userEmail = "testuser@email.com";
        return bookService.checkoutBook(userEmail,bookId);
    }
}
