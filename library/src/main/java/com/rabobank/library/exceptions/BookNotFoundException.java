package com.rabobank.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

        public BookNotFoundException() {
            super();
        }
        public BookNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
        public BookNotFoundException(String message) {
            super(message);
        }
        public BookNotFoundException(Throwable cause) {
            super(cause);
        }
}
