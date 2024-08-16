package com.backend.restapi.spring_boot_library.controller;

import com.backend.restapi.spring_boot_library.entity.Book;
import com.backend.restapi.spring_boot_library.service.BookService;
import com.backend.restapi.spring_boot_library.utils.ExtractJWT;
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
    public int currentLoan(@RequestHeader(value = "Authorization") String token){
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return bookService.currentLoanCount(userEmail);
    }

    @GetMapping("/secure/isCheckedOut/byUser")
    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token,@RequestParam Long bookId){
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return bookService.checkoutBookByUser(userEmail,bookId);
    }

    @PostMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String token,@RequestParam Long bookId)throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        return bookService.checkoutBook(userEmail,bookId);
    }
}
