package com.rabobank.library.service;

import com.rabobank.library.dao.BookDao;
import com.rabobank.library.entity.Book;
import com.rabobank.library.entity.BookDto;

import com.rabobank.library.validation.PayloadValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";

    @Autowired
    private PayloadValidation payloadValidation;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public Collection<BookDto> getBooks(String author,String title){
        if(author==null && title==null)
            return bookDao.getBooks();
        else
            return bookDao.getBooksByTitleOrAuthor(author,title);
    }

    public Book getBookByIsbnNumber(String isbnNumber){
        return  bookDao.getBookByIsbnNumber(isbnNumber);
    }

    @Async
    public CompletableFuture<String> orderBooks(List<BookDto> books) throws InterruptedException, ExecutionException {
        logger.info("Validation Message "+payloadValidation.isPayloadValid(books));
        CompletableFuture<String> validationMessage = new CompletableFuture<String>();
        validationMessage.complete("Invalid Payload");
        if(!payloadValidation.isPayloadValid(books)){
            return validationMessage;
        }

        logger.info("Book Order starts :"+Thread.currentThread().getName());
        String result=restTemplate.postForObject(BASE_URL,books,String.class);
        Thread.sleep(5000L);
        logger.info("Complete");
        return CompletableFuture.completedFuture(result);
    }
}

