package rest.service.model;

import java.util.ArrayList;
import java.util.List;

public class Movies extends Item {
    private String director;
    private List<String> writers = new ArrayList<>();
    private List<String> stars = new ArrayList<>();

    public Movies() {
    }

    public Movies(String title, String genre, String format, Integer year, String director) {
        super(title, genre, format, year);
        this.director = director;
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(ArrayList<String> writers) {
        this.writers = writers;
    }

    public List<String> getStars() {
        return stars;
    }

    public void setStars(ArrayList<String> stars) {
        this.stars = stars;
    }

    public void addWriter(String writer) {
        this.writers.add(writer);
    }

    public void addStar(String star) {
        this.stars.add(star);
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

