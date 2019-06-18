package rest.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import rest.service.model.Books;
import rest.service.model.Item;

public class Scraper {

    public Item scrapeAndGetItem(Document document) {
        Elements elements = document.select("div.media-details");

        if (elements == null || elements.isEmpty()) {
            return null; //not an item page
        }

        return new Books();
    }
}