package com.rabobank.library.dao;

import com.rabobank.library.entity.Book;
import com.rabobank.library.entity.BookDto;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface BookDao {

    Collection<BookDto> getBooks();

    Collection<BookDto> getBooksByTitleOrAuthor(String author,String title);

    Book getBookByIsbnNumber(String id);
}
