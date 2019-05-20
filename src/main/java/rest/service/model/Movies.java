package rest.service.model;

import java.util.ArrayList;

public class Movies extends Item {
    private String director;
    private ArrayList<String> writers;
    private ArrayList<String> stars;

    public Movies() { }

    public Movies(String genre, String format, Integer year, String director) {
        super(genre, format, year);
        this.director = director;
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public ArrayList<String> getWriters() {
        return writers;
    }

    public void setWriters(ArrayList<String> writers) {
        this.writers = writers;
    }

    public ArrayList<String> getStars() {
        return stars;
    }

    public void setStars(ArrayList<String> stars) {
        this.stars = stars;
    }

    public void addWriters(String writer){
        this.writers.add(writer);
    }

    public void addStars(String star){
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

