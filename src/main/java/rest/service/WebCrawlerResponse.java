package rest.service;


public class WebCrawlerResponse {
    private final Statistic statistic;

    public WebCrawlerResponse(Statistic statistic) {
        this.statistic = statistic;
    }

    public Statistic getStatistic() {
        return statistic;
    }
}
