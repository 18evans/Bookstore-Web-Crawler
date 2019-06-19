package rest.service;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rest.service.model.Item;
import rest.service.model.Movies;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WebCrawler {

    private Statistic statistic;
    private URL url;
    private Scraper scraper;
    private final Set<URL> exploredUrls;
    private final Set<URL> toBeExploredUrls;
    private Set<Item> foundItems;

    public WebCrawler(URL url, String keyword, Item type) {
        this.url = url;
        if (keyword == null || keyword.equals("")) {
            throw new IllegalArgumentException();
        }
        statistic = new Statistic(type, keyword);
        exploredUrls = new HashSet<>();
        toBeExploredUrls = Collections.singleton(url);
        foundItems = new HashSet<>();
        this.scraper = new Scraper();
    }

    /***
     * Set the Statistic object - for mocking purpose
     * @param statistic
     */
    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    /**
     * Method calls private crawl which will recursively go through the URL,
     * looking for the specified objects either by using BFS or DFS searching algorithm.
     *
     * @return A Set Collection of the Item
     */
    public Set<Item> startCrawler() throws IOException {
        return crawl(toBeExploredUrls);
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
        urls.removeAll(this.exploredUrls);
        if (!urls.isEmpty()) {
            this.exploredUrls.addAll(urls);
            final Set<URL> newUrls = new HashSet<>();
            try {
                for (final URL url : toBeExploredUrls) {
                    this.statistic.increasePagesExplored();
                    final Document document = Jsoup.connect(url.toString()).get();
                    final Elements urlsOnPage = document.select("a[href]");
                    Item newFoundItem = (Item) scraper.scrapeAndGetItem(document);
                    if (newFoundItem != null && foundItems.stream().noneMatch(o -> o.compareTo(newFoundItem))) {
                        //do not add if no new found element or an element with same properties exists
                        foundItems.add(newFoundItem);
                    }
                    for (final Element element : urlsOnPage) {
                        final String urlText = element.attr("abs:href");
                        final URL discoveredUrl = new URL(urlText);
                        newUrls.add(discoveredUrl);
                    }
                }
            } catch (Exception ex) {

            }
            this.statistic.increaseSearchDepth();
            return crawl(newUrls);
        } else {
            // filter all the item founds to match the searching criteria
            return process(foundItems);
        }
    }

    /***
     * Filter out all un-matched item and return the collection of close-matched items
     * @param foundItems - the raw result collection
     * @return
     */
    private Set<Item> process(Set<Item> foundItems) {
        Set<Item> result = new HashSet<>();
        String targetKeyword = this.statistic.getKeyword();

        for (Item i : foundItems) {
            if (StringUtils.isBlank(targetKeyword) ||
                    i.getTitle().contains(targetKeyword) ||
                    i.getGenre().contains(targetKeyword) ||
                    i.getYear().toString().contains(targetKeyword) ||
                    i.getFormat().contains(targetKeyword)) {
                result.add(i);
            }
        }

        return result;
    }

    /***
     * Return the initial url
     * @return
     */
    public String getInitUrl() {
        return this.url.toString();
    }

    /***
     * return the keyword
     * @return
     */
    public String getKeyword() {
        return statistic.getKeyword();
    }

    public Item getItem() {
        return statistic.getType();
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public Set<URL> getExploredUrls() {
        return exploredUrls;
    }


    public Set<URL> getToBeExploredUrls() {
        return toBeExploredUrls;
    }


    /***
     * Set the Scraper object - for mocking purpose
     * @param scraper
     */
    public void setScraper(Scraper scraper) {
        this.scraper = scraper;
    }

    /***
     * Change the target keyword and reset the statistic to start a new crawling process.
     * @param newKeyword
     */
    public void changeKeyword(String newKeyword) {
        this.statistic.changeKeyword(newKeyword);
        this.statistic.resetData();
    }

    /***
     * Change the target type and reset the statistic to start a new crawling process
     * @param newType
     */
    public void changeType(Item newType) {
        this.statistic.changeTargetType(newType);
        this.statistic.resetData();
    }

    /***
     * Retunr the current search depth after the crawling process already finished
     * @return
     */
    public Integer getSearchDepth() {
        return this.statistic.getSearchDepth();
    }
}
