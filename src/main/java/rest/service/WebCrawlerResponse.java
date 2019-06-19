package rest.service;


import rest.service.model.Books;
import rest.service.model.Item;
import rest.service.model.Movies;
import rest.service.model.Music;

import java.util.HashSet;
import java.util.Set;

public class WebCrawlerResponse {
    private final Statistic statistic;
    private final Set<Books> books = new HashSet<>();
    private final Set<Movies> movies = new HashSet<>();
    private final Set<Music> music = new HashSet<>();

    public WebCrawlerResponse(Statistic statistic, Set<Item> results) {
        this.statistic = statistic;
        for (Item item : results) {
            if (item instanceof Books) {
                books.add((Books) item);
            } else if (item instanceof Movies) {
                movies.add((Movies) item);
            } else if (item instanceof Music) {
                music.add((Music) item);
            }
        }
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public Set<Books> getBooks() {
        return books;
    }

    public Set<Movies> getMovies() {
        return movies;
    }

    public Set<Music> getMusic() {
        return music;
    }

}
