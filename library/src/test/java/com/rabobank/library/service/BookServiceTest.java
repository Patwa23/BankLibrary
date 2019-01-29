package com.rabobank.library.service;

import com.rabobank.library.dao.BookDao;
import com.rabobank.library.entity.Book;
import com.rabobank.library.entity.BookDto;
import com.rabobank.library.validation.PayloadValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookService.class)
public class BookServiceTest {

    @MockBean
    private BookDao bookDao;

    @MockBean
    private PayloadValidation payloadValidation;

    @MockBean
    private RestTemplate restTemplate;

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";

    List<BookDto> mockBooksDto = List.of(new BookDto("1","Lord of the Ring","Lord"),
            new BookDto("2","Lord of the earth","Arnold"),
            new BookDto("3","Lord of the space","Earth"));

    @Test
    public void getBooks() {

        Mockito.when(bookDao.getBooks()).thenReturn(mockBooksDto);
        assertEquals(3,bookDao.getBooks().size());

        List<BookDto> mockBooksByTitleOrAuthor = List.of(new BookDto("1","Lord of the Ring","Lord"),
                                                         new BookDto("3","Lord of the space","Earth"));

        Mockito.when(bookDao.getBooksByTitleOrAuthor("earth","ring")).thenReturn(mockBooksByTitleOrAuthor);
        assertEquals(2,bookDao.getBooksByTitleOrAuthor("earth","ring").size());
    }

    @Test
    public void getBookByIsbnNumber() {
        Book mockBook = new Book("1","Lord of the Ring","Lord","This is European Book");
        Mockito.when(bookDao.getBookByIsbnNumber("1")).thenReturn(mockBook);
        assertEquals(mockBook,bookDao.getBookByIsbnNumber("1"));
    }

    @Test
    public void orderBooks() throws InterruptedException, ExecutionException {
        System.out.println("Test Book Order starts "+ Thread.currentThread().getName());
        String result=restTemplate.postForObject(BASE_URL,mockBooksDto,String.class);
        Thread.sleep(1000L);
        System.out.println("Result "+ result);
    }
}