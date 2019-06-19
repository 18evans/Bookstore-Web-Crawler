package rest.service.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Books extends Item {
    private List<String> authors = new ArrayList<>();
    private String publisher;
    private String ISBN;

    public Books() {
    }

    public Books(String title, String genre, String format, Integer year, List<String> authors, String publisher, String ISBN) {
        super(title, genre, format, year);
        this.authors = authors;
        this.publisher = publisher;
        this.ISBN = ISBN;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void addAuthor(String authors) {
        this.authors.add(authors);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public boolean compareTo(Item obj) {
        if (super.compareTo(obj)
                && publisher.equals(((Books) obj).publisher)
                && ISBN.equals(((Books) obj).ISBN)
                && Arrays.equals(authors.toArray(), ((Books) obj).authors.toArray())) {
            return true;
        }
        return false;
    }
}

