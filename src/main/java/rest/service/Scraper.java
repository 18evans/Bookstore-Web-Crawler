package rest.service;

import rest.service.model.Item;

import java.net.URL;
import java.util.Set;

public interface Scraper {
    Set<Item> findItem(URL url);
}
