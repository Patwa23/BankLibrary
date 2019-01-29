package com.rabobank.library.validation;

import com.rabobank.library.entity.Book;
import com.rabobank.library.entity.BookDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class PayloadValidation {

    boolean isValid=false;

    public boolean isPayloadValid(List<BookDto> books){
        System.out.println("Test2");
        if(isPayloadBlank(books))
            return isValid;

        isValid=true;
        return isValid;
    }

    private boolean isPayloadBlank(List<BookDto> books){
        if(books.isEmpty())
            return true;
        return false;
    }

}
