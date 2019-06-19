package rest.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rest.service.model.Books;
import rest.service.model.Item;
import rest.service.model.Movies;
import rest.service.model.Music;

public class Scraper {

    public Item scrapeAndGetItem(Document document) {
        Elements elements = document.select("div.media-details");

        if (elements == null || elements.isEmpty()) {
            return null; //not an item page
        }

        final String title = elements.select("h1").text();
        final Elements rows = elements.select("table tbody tr");

        Item item = null;
        String category = "", genre = "", format = "";
        int year = -1;

        for (Element row : rows) {
            if (row.select("th").text().toLowerCase().contains("genre")) {
                genre = row.select("td").text();
            } else if (row.select("th").text().toLowerCase().contains("format")) {
                format = row.select("td").text();
            } else if (row.select("th").text().toLowerCase().contains("year")) {
                year = Integer.parseInt(row.select("td").text());
            } else if (row.select("th").text().toLowerCase().contains("category")) {
                category = row.select("td").text();
            }
        }

        switch (category.toLowerCase()) {
            case "books":
                item = new Books();

                String authors = "", publisher = "", isbn = "";

                for (Element row : rows) {
                    if (row.select("th").text().toLowerCase().contains("authors")) {
                        authors = row.select("td").text();
                    } else if (row.select("th").text().toLowerCase().contains("publisher")) {
                        publisher = row.select("td").text();
                    } else if (row.select("th").text().toLowerCase().contains("isbn")) {
                        isbn = row.select("td").text();
                    }
                }

                String[] splitAuthors = authors.split(", ");
                for (String author : splitAuthors) {
                    ((Books) item).addAuthor(author);
                }

                ((Books) item).setPublisher(publisher);
                ((Books) item).setISBN(isbn);
                break;
            case "movies":
                item = new Movies();

                String director = "", writers = "", stars = "";

                for (Element row : rows) {
                    if (row.select("th").text().toLowerCase().contains("director")) {
                        director = row.select("td").text();
                    } else if (row.select("th").text().toLowerCase().contains("writers")) {
                        writers = row.select("td").text();
                    } else if (row.select("th").text().toLowerCase().contains("stars")) {
                        stars = row.select("td").text();
                    }
                }

                ((Movies) item).setDirector(director);

                String[] splitWriters = writers.split(", ");
                for (String writer : splitWriters) {
                    ((Movies) item).addWriter(writer);
                }

                String[] splitStars = stars.split(", ");
                for (String star : splitStars) {
                    ((Movies) item).addStar(star);
                }
                break;
            case "music":
                item = new Music();

                for (Element row : rows) {
                    if (row.select("th").text().toLowerCase().contains("artist")) {
                        String artist = row.select("td").text();
                        ((Music) item).setArtist(artist);
                    }
                }
                break;
        }

        item.setTitle(title);
        item.setFormat(format);
        item.setGenre(genre);
        item.setYear(year);

        return item;
    }
}