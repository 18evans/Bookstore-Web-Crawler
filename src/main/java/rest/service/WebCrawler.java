package rest.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rest.service.model.Item;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {

    private Statistic statistic;
    private Scrape scrape;
    private URL url;
    private Set<URL> urlList;

    public WebCrawler(URL url, String keyword, Item type) {
        this.url = url;
        if (keyword == null || keyword.equals("")) {
            throw new IllegalArgumentException();
        }
        statistic = new Statistic(type, keyword);
        urlList = new HashSet<>();
        urlList.add(url);
    }

    /**
     * Method calls private crawl which will recursively go through the URL,
     * looking for the specified objects either by using BFS or DFS searching algorithm.
     *
     * @return A Set Collection of the Item
     */
    public Set<Item> startCrawler() throws IOException {
        URL discoveredUrl = new URL("fontys.nl");
        this.urlList.add(discoveredUrl);

        return null;
    }

    /**
     * Method wil recursively go through the passed collection of url parameter,
     * until it reaches the end recursively adding it's finding to its return type of
     * a Set of Item objects.
     *
     * @param urls the url's left to go through
     * @return the found Items
     */
    private Set<Item> crawl(Set<URL> urls) {
        return null;
    }

    /***
     * Return the initial url
     * @return
     */
    public String getInitUrl() {
        return this.url.toString();
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

    public Set<URL> getUrlList() {
        return urlList;
    }
}
