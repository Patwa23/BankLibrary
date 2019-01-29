package com.rabobank.library.controller;

import com.rabobank.library.entity.BookDto;
import com.rabobank.library.service.BookService;
import com.rabobank.library.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    /*Get a list of books.The books have an ISBN number, title and author.
     &
     Returns the books which match the query. You should be able to search for partial titles and authors. I.e. a search for “lord” finds “lord of the rings”.
    */
    @GetMapping
    public Collection<BookDto> getBooks(@RequestParam(required = false) final String author,
                                          @RequestParam(required = false) final String title){
       return this.bookService.getBooks(author,title);
    }

    //Returns the book with the given ISBN number. An additional field on the book object “summary” is also returned.
    @GetMapping(value="/{isbnNumber}")
    public Book getBookByIsbnNumber(@PathVariable("isbnNumber") final String isbnNumber){
        return this.bookService.getBookByIsbnNumber(isbnNumber);
    }

    //This endpoint should allow the user to order multiple books
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<String> orderBooks(@Valid @RequestBody List<BookDto> books) throws InterruptedException, ExecutionException {
        CompletableFuture<String> book = this.bookService.orderBooks(books);

        while (true) {
            if (book.isDone()) {
                logger.info("Result from asynchronous process - " + book.get());
                return book;
            }
            logger.info("Continue async call .... ");
            Thread.sleep(1000);
        }
    }

}
