package rest.service.model;

public class Books extends Item {
    private String authors;
    private String publisher;
    private String ISBN;

    public Books() { }

    public Books(String genre, String format, Integer year, String authors, String publisher, String ISBN) {
        super(genre, format, year);
        this.authors = authors;
        this.publisher = publisher;
        this.ISBN = ISBN;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

