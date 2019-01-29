package com.rabobank.library.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {
    @NotNull(message="ISBN Number cannot be null")
    private String isbnNumber;
    @NotNull(message="Title cannot be null")
    @Size(min=3,message="Title must not be less than 3 characters")
    private String title;
    @NotNull(message="Author cannot be null")
    @Size(min=3,max=50,message="Author must be equal or greater than 3 characters and less than 50 characters")
    private String author;
    @Size(min=8,message="Title must not be less than 8 characters")
    private String summary;

    public Book(String isbnNumber, String title, String author, String summary) {
        this.isbnNumber = isbnNumber;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }








}
