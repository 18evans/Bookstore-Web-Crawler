package rest.service;


import rest.service.model.Item;

import java.util.Set;

public class WebCrawlerResponse {
    private final Statistic statistic;
    private final Set<Item> results;

    public WebCrawlerResponse(Statistic statistic, Set<Item> results) {
        this.statistic = statistic;
        this.results = results;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public Set<Item> getResults() {
        return results;
    }

}
