package com.rabobank.library.transformer;

import com.rabobank.library.entity.Book;
import com.rabobank.library.entity.BookDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BookToBookDtoTransformer implements Function<Book, BookDto> {

    @Override
    public BookDto apply(Book book){
        BookDto dto =new BookDto();
        dto.setIsbnNumber(book.getIsbnNumber());
        dto.setAuthor(book.getAuthor());
        dto.setTitle(book.getTitle());
        return dto;
    }
}
