package rest.service;

import rest.service.model.Item;

public class Statistic {
    private Long startTime;
    private Integer pagesExplored;
    private Integer searchDepth;
    private Item type;
    private String keyword;

    public Statistic(Item type, String keyword) {
        this.type = type;
        this.keyword = keyword;
        this.startTime = System.currentTimeMillis();
        this.pagesExplored = 0;
        this.searchDepth = 0;
    }

    public Statistic(Item type, String keyword, Long startTime) {
        this.type = type;
        this.keyword = keyword;
        this.startTime = startTime;
        this.pagesExplored = 0;
        this.searchDepth = 0;
    }

    public Integer getSearchDepth() {
        return searchDepth;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Integer getPagesExplored() {
        return pagesExplored;
    }

    public Item getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }

    /***
     * Increase the total number of pages explored by one.
     */
    public void increasePagesExplored(){
        this.pagesExplored += 1;
    }

    /***
     * Increase the search depth by one if and only if the page is not in the same depth level
     */
    public void increaseSearchDepth(){
        this.searchDepth += 1;
    }

    /***
     * Change the target keyword of the crawling process.
     * @param newKeyword - The new searching keyword
     */
    public void changeKeyword(String newKeyword){
        this.keyword = newKeyword;
    }

    /***
     * Change the target type of the crawling process
     * @param newType - The new type which derives from Item class
     */
    public void changeTargetType(Item newType){
        this.type = newType;
    }

    /***
     * Reset the statistic to the initial values. The method should be used when the keyword/target type is changed
     */
    public void resetData() {
        this.searchDepth = 0;
        this.pagesExplored = 0;
    }
}
