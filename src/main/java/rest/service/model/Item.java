package rest.service.model;

public abstract class Item {
    private String genre;
    private String format;
    private Integer year;

    public Item() { }

    public Item(String genre, String format, Integer year) {
        this.genre = genre;
        this.format = format;
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public String getFormat() {
        return format;
    }

    public Integer getYear() {
        return year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setYear(Integer year) {
        this.year = year;
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
