package com.rabobank.library.dao;

import com.rabobank.library.entity.Book;
import com.rabobank.library.entity.BookDto;
import com.rabobank.library.transformer.BookToBookDtoTransformer;
import com.rabobank.library.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FakeBookDaoImp implements BookDao {

    @Autowired
    private BookToBookDtoTransformer bookToBookDtoTransformer;

    private List<Book> books = List.of(
            new Book("1","Lord of the Ring","Lord","This is European Book"),
            new Book("2","Lord of the earth","Arnold","This is Asian book"),
            new Book("3","Lord of the space","Earth","This is American book")
    );

    public Collection<BookDto> getBooks(){
        return this.books.stream()
                         .map(book -> bookToBookDtoTransformer.apply(book))
                         .collect(Collectors.toList());
    }
    public Collection<BookDto> getBooksByTitleOrAuthor(String author,String title){
        return this.books.stream()
                .map(book -> bookToBookDtoTransformer.apply(book))
                .filter( b-> (author!=null && b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                        || (title!=null && b.getTitle().toLowerCase().contains(title.toLowerCase())))
                .collect(Collectors.toList());
    }

    public Book getBookByIsbnNumber(String isbnNumber){
        Book book = books.stream()
                         .filter(b -> b.getIsbnNumber()!=null && b.getIsbnNumber().equalsIgnoreCase(isbnNumber))
                         .findFirst()
                         .orElse(null);
        if (book == null)
            throw new BookNotFoundException("No such Book by ISBN Number:"+isbnNumber);
        return book;
    }

}
