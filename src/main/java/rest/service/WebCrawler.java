package rest.service;

import rest.service.model.Item;

import java.net.URL;
import java.util.Set;

public class WebCrawler {

    private Statistic statistic;
    private Scrape scrape;
    private URL url;

    public WebCrawler(URL url, String keyword, Item type) {
        this.url = url;
        if (keyword == null || keyword.equals("")){
            throw new IllegalArgumentException();
        }
        statistic = new Statistic(type, keyword);
    }

    /**
     * Method calls private crawl which will recursively go through the URL,
     * looking for the specified objects either by using BFS or DFS searching algorithm.
     * @param startURL url of the site we wish to crawl through
     * @param type of object we're looking for. See class name of abstract Item implementations.
     * @param keyword we're looking for within the body of the site of the passed url.
     * @return A Set Collection of the Item
     */
    public Set<Item> startCrawler(URL startURL, Class type, String keyword){
        return null;
    }

    /**
     * Method wil recursively go through the passed collection of url parameter,
     * until it reaches the end recursively adding it's finding to its return type of
     * a Set of Item objects.
     * @param urls the url's left to go through
     * @return the found Items
     */
    private Set<Item> crawl(Set<URL> urls) {
        return null;
    }

    public String getInitUrl() {
        return this.url.getHost();
    }

    public String getKeyword() {
        return statistic.getKeyword();
    }

    public Object getItem() {
        return statistic.getType();
    }

    public Statistic getStatistic() {
        return statistic;
    }
}
