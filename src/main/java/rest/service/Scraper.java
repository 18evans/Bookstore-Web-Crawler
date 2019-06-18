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

        final Elements rows = elements.select("table tbody tr");

        Item item;
        String category = "";
        for (Element row : rows) {
            if (row.select("th").text().toLowerCase().contains("category")) {
                category = row.select("td").text();
            }
        }

        switch (category.toLowerCase()) {
            case "books":
                item = new Books();
                break;
            case "movies":
                item = new Movies();
                break;
            case "music":
                item = new Music();
                break;
            default:
                throw new IllegalStateException("Encountered an Item from a Category which is not supported yet!");
        }

        return item;
    }
}