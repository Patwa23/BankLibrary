package com.rabobank.library.controller;


import com.rabobank.library.entity.Book;
import com.rabobank.library.entity.BookDto;
import com.rabobank.library.service.BookService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookController.class)
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    List<BookDto> mockBookDto = Arrays.asList(new BookDto("1", "Lord of the Ring", "Lord"));
    Book mockBook =new Book("1","Lord of the Ring","Lord","This is European Book");

    String exampleInputJson ="[\n" +
            "\t{\n" +
            "\t\t\"isbnNumber\":\"1\",\n" +
            "\t    \"title\": \"Lo\",\n" +
            "\t    \"author\": \"Lord\"\n" +
            "\t}\n" +
            "]";

    @Test
    public void getBooks() throws Exception{

        Mockito.when(bookService.getBooks(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(mockBookDto);

        MvcResult result = mockMvc.perform(get("/books")
                                  .accept(MediaType.APPLICATION_JSON))
                                  .andExpect(status().isOk())
                                  .andReturn();

        assertEquals(1,bookService.getBooks("","").size());
    }

    @Test
    public void getBookByIsbnNumber() throws Exception{

        Mockito.when(bookService.getBookByIsbnNumber(Mockito.anyString())).thenReturn(mockBook);

        MvcResult result = mockMvc.perform(get("/books/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbnNumber", Matchers.is("1")))
                .andExpect(jsonPath("$.title", Matchers.is("Lord of the Ring")))
                .andExpect(jsonPath("$.author", Matchers.is("Lord")))
                .andExpect(jsonPath("$.summary", Matchers.is("This is European Book")))
                .andExpect(jsonPath("$.*", Matchers.hasSize(4)))
                .andReturn();
    }

    @Test
    public void orderBooks() throws InterruptedException, ExecutionException,Exception {
        CompletableFuture<String> mockBook = new CompletableFuture<String>();
        mockBook.complete("{\n" +
                "  \"0\": {\n" +
                "    \"isbnNumber\":  \"1\",\n" +
                "    \"title\": \"Lo\",\n" +
                "    \"author\": \"Lord\",\n" +
                "  },\n" +
                "  \"id\": 101\n" +
                "}");

        Mockito.when(bookService.orderBooks(Mockito.anyList())).thenReturn(mockBook);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/books")
                .accept(MediaType.APPLICATION_JSON).content(exampleInputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(mockBook,bookService.orderBooks(mockBookDto));
    }
}