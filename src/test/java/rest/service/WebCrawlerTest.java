package rest.service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import rest.service.model.Books;
import rest.service.model.Item;
import rest.service.model.Movies;
import rest.service.model.Music;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class WebCrawlerTest {
    private String emptyString;
    private String nullString;
    private String validKeyword;
    private Item validBookType, validMovieType, validMusicType;
    private Item validGeneralItemType;
    private URL validUrl;
    private Scraper scraperDummy;
    private Statistic statisticDummy;

    @Before
    public void setUp() throws MalformedURLException {
        emptyString = "";
        nullString = null;
        validKeyword = "sample keyword";
        validBookType = mock(Books.class);
        validMovieType = mock(Movies.class);
        validMusicType = mock(Music.class);
        validGeneralItemType = mock(Item.class);
        scraperDummy = mock(Scraper.class);
        statisticDummy = mock(Statistic.class);
        validUrl = new URL("https://fontys.nl");
    }

    private URL[] crawlOneSiteToManySites() throws MalformedURLException {
        return new URL[]{
                new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=101"),
                new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=201"),
                new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=301")
        };
    }

    /**
     * The method that feed test data that can still be crawled to the web crawler.
     * @return
     * @throws MalformedURLException
     */
    private Object[] testSampleWebForCrawling() throws MalformedURLException {
        return new Object[]{
          new Object[]{ new URL("http://i367506.hera.fhict.nl/webcrawl_example/catalog.php?cat=books")},
          new Object[]{ new URL("http://i367506.hera.fhict.nl/webcrawl_example/catalog.php?cat=movies")},
          new Object[]{ new URL("http://i367506.hera.fhict.nl/webcrawl_example/catalog.php?cat=music")},
        };
    }


    /***
     * The test that verify the method should throw an MalformedURLException when
     * the given URL is Empty or Null.
     */
    @Test(expected = MalformedURLException.class)
    public void nullOrEmptyURLShouldThrowIllegalArgumentException() throws MalformedURLException {
        // arrange
        URL emptyUrl = new URL(emptyString);
        URL nullURL = new URL(nullString);

        // act
        WebCrawler webCrawler = new WebCrawler(emptyUrl, validKeyword, validGeneralItemType);
        WebCrawler webCrawler2 = new WebCrawler(nullURL, validKeyword, validGeneralItemType);

        // assert
    }

    /***
     * The test which checks that the webcrawler class should not throw any exception if the given
     * url is in a correct format.
     */
    @Test
    public void validUrlShouldNotThrowException() {
        //  arrange
        WebCrawler webCrawler;
        String expectedUrl = validUrl.toString();

        // act
        webCrawler = new WebCrawler(validUrl, validKeyword, validGeneralItemType);
        String actualUrl = webCrawler.getInitUrl();

        // assert
        assertNotNull("The webcrawler is null", webCrawler);
        assertEquals("The url did not match", expectedUrl, actualUrl);

    }

    /***
     * The test that verify the method should throw an IllegalArgumentException when
     * the given keyword is Empty or Null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void emptyKeywordShouldThrowIllegalArgumentException() {
        // arrange
        String emptyKeyword = emptyString;
        WebCrawler webCrawler;

        // act
        webCrawler = new WebCrawler(validUrl, emptyKeyword, validMusicType);
        webCrawler = new WebCrawler(validUrl, emptyKeyword, validBookType);
        webCrawler = new WebCrawler(validUrl, emptyKeyword, validMovieType);
    }

    /***
     * The test that verify the method should throw an IllegalArgumentException when
     * the given keyword is Empty or Null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void nullKeywordShouldThrowIllegalArgumentException() {
        // arrange
        String nullKeyword = nullString;
        WebCrawler webCrawler;

        // act
        webCrawler = new WebCrawler(validUrl, nullKeyword, validBookType);
        webCrawler = new WebCrawler(validUrl, nullKeyword, validMovieType);
        webCrawler = new WebCrawler(validUrl, nullKeyword, validMusicType);
    }

    /***
     * The test the test that verify the method should not throw an IllegalArgumentException when
     * the given keyword is in a valid format
     */
    @Test
    public void validKeywordShouldNotThrowIllegalArgumentException() {
        // arrange
        WebCrawler webCrawler;

        // act
        webCrawler = new WebCrawler(validUrl, validKeyword, validGeneralItemType);

        // assert
        assertNotNull("The WebCrawler object was null!!", webCrawler);
    }

    /***
     * A constructor test which check all the fields of the WebCrawler class
     */
    @Test
    public void afterInstantiationAllFieldsShouldBeSet() {
        // arrange
        WebCrawler webCrawler;

        // act
        webCrawler = new WebCrawler(validUrl, validKeyword, validGeneralItemType);
        String actualUrl = webCrawler.getInitUrl();
        String actualKeyword = webCrawler.getKeyword();
        Statistic actualStatistic = webCrawler.getStatistic();
        Object actualItem = webCrawler.getItem();

        // assert
        assertEquals("The url did not match!!", actualUrl, validUrl.toString());
        assertEquals("The keyword did not match!!", actualKeyword, validKeyword);
        assertNotNull("The Item was null!!", actualItem);
        assertTrue("The Item did not implement Item class", actualItem instanceof Item);
        assertNotNull("The statistic object was null!!", actualStatistic);
    }

    /***
     * A test which check the initial set of url. The initial set should have only one url.
     */
    @Test
    public void afterInstantiationTheToBeExploredUrlShouldHaveOnlyOneInitUrl() {
        // arrange
        WebCrawler webCrawler = new WebCrawler(validUrl, validKeyword, validGeneralItemType);
        Integer expectedNrOfUrl = 1;
        String expectedUrlString = validUrl.toString();

        // act
        Integer actualNrOfUrl = webCrawler.getToBeExploredUrls().size();
        String actualUrlString = webCrawler.getToBeExploredUrls().toArray()[0].toString();

        // assert
        assertEquals("The number of initial url was not 1", actualNrOfUrl, expectedNrOfUrl);
        assertEquals("The url did not match", actualUrlString, expectedUrlString);
    }

    /***
     * This is a parameterized test which uses urls (that contain more url within it).
     * When the crawl process start, the url list should be increased to more than one.
     */
    @Test
    @Parameters(method = "crawlOneSiteToManySites")
    public void afterStartTheUrlListShouldHaveMoreThanZeroUrl(URL url) throws IOException {
        // arrange
        WebCrawler webCrawler = new WebCrawler(url, validKeyword, validGeneralItemType);

        // act
        webCrawler.startCrawler();

        // assert
        assertTrue("The explored URL list had zero url!!!", webCrawler.getExploredUrls().size() > 0);
        assertNotNull("The web crawler was null", webCrawler);
    }

    /**
     * After finish searching for all the links of the given url, the total number of page explored should be increase by one.
     *
     * @param url - the given URL
     * @throws IOException - expected exception from Jsoup
     */
    @Test
    @Parameters(method = "crawlOneSiteToManySites")
    public void afterFinishFindingAllLinksOfAnUrlTheStatisticShouldIncreaseTheNumberOfPageExplored(URL url) throws IOException {
        // arrange
        WebCrawler webCrawler = new WebCrawler(url, validKeyword, validGeneralItemType);
        Statistic statistic = mock(Statistic.class);
        webCrawler.setStatistic(statistic);

        // act
        webCrawler.startCrawler();
        Integer actualNrOfPageExplored = webCrawler.getExploredUrls().size();

        // assert
        verify(statistic, atLeast(2)).increasePagesExplored();
        assertTrue("The total number of page explored was not increased", actualNrOfPageExplored > 0);
    }


    /***
     * When the crawling process could not find anything and the new url set to be explored
     * are empty. The method should return an empty collection.
     * @param url - the init url
     */
    @Test
    @Parameters(method = "crawlOneSiteToManySites")
    public void ifTheUrlSetIsEmptyButNotFoundAnythingShouldEmptyCollection(URL url) throws IOException {
        // arrange
        WebCrawler webCrawler = new WebCrawler(url, validKeyword, validGeneralItemType);
        webCrawler.setStatistic(statisticDummy);
        when(validGeneralItemType.getFormat()).thenReturn("sddad");
        when(validGeneralItemType.getGenre()).thenReturn("9588231asda");
        when(validGeneralItemType.getTitle()).thenReturn("a123132nasdsad");
        when(validGeneralItemType.getYear()).thenReturn(404040);
        when(statisticDummy.getKeyword()).thenReturn(validKeyword);

        // act
        Set<Item> actualResult = webCrawler.startCrawler();

        // arrange
        assertTrue("The item collection was not empty!!", actualResult.size() == 0);
        verify(statisticDummy).getKeyword();
    }

    /**
     * Finding an Item from the Scraper should result in that Item being returned in
     * the {@link WebCrawler#startCrawler()} Set return type collection.
     * Tested on a cleared results Set.
     */
    @Test
    @Parameters(method = "crawlOneSiteToManySites")
    public void itemFoundInScraperShouldBePutInCrawlResultSet(URL url) throws IOException {
        // arrange
        WebCrawler webCrawler = new WebCrawler(url, validKeyword, validGeneralItemType);

        webCrawler.setStatistic(statisticDummy);
        when(statisticDummy.getKeyword()).thenReturn(validKeyword);

        webCrawler.setScraper(scraperDummy);
        when(scraperDummy.scrapeAndGetItem(any())).thenReturn(validGeneralItemType);

        when(validGeneralItemType.getFormat()).thenReturn("sddad");
        when(validGeneralItemType.getGenre()).thenReturn("9588231asda");
        when(validGeneralItemType.getTitle()).thenReturn("asdsa" + validKeyword); //title will match keyword
        when(validGeneralItemType.getYear()).thenReturn(404040);


        // act
        Set<Item> actualResult = webCrawler.startCrawler();

        // arrange
        assertThat("The result collection did not contain the item!!", actualResult, contains(validGeneralItemType));
        verify(statisticDummy, atLeast(1)).increasePagesExplored();
    }


    /***
     * During the crawling process, if the current collection of URLs are not empty
     * and no result found yet, the crawling should continue to the next URL in the
     * collection of URLs
     * @param initUrl - the current collection of URLs
     */
    @Test
    @Parameters(method = "testSampleWebForCrawling")
    public void ifTheUrlSetIsNotEmptyButNotFoundAnythingShouldContinueWithTheNextUrl(URL initUrl) throws IOException {
        // arrange
        WebCrawler webCrawler = new WebCrawler(initUrl, validKeyword, validGeneralItemType);

        webCrawler.setStatistic(statisticDummy);

        webCrawler.setScraper(scraperDummy);
        when(scraperDummy.scrapeAndGetItem(any())).thenReturn(null);

        // act
        Set<Item> actualResult = webCrawler.startCrawler();

        // assert
        assertThat("The result collection contained unwanted item!!", actualResult, not(hasItem(validGeneralItemType)));
        verify(statisticDummy, atLeast(2)).increasePagesExplored();
        verify(scraperDummy, atLeast(2)).scrapeAndGetItem(any());
    }

    /***
     * A test which checks when the keyword is changed, the web crawler must reset all previous data.
     */
    @Test
    public void whenTheKeywordIsChangedTheStatisticShouldResetAllData(){
        // arrange
        String oldKeyword = "old";
        String newKeyword = "new";
        WebCrawler webCrawler = new WebCrawler(validUrl, oldKeyword, validGeneralItemType);
        webCrawler.setStatistic(statisticDummy);
        webCrawler.setScraper(scraperDummy);

        // act
        webCrawler.changeKeyword(newKeyword);

        // assert
        verify(statisticDummy).resetData();
        verify(statisticDummy).changeKeyword(newKeyword);
    }

    /**
     * A test which checks when the target type is changed, the web crawler must reset all previous data.
     */
    @Test
    public void whenTheTargetTypeIsChangedTheStatisticShouldResetAllData(){
        // arrange
        Books oldType = (Books) validBookType;
        Movies newType = (Movies) validMovieType;
        WebCrawler webCrawler = new WebCrawler(validUrl, validKeyword, oldType);
        webCrawler.setStatistic(statisticDummy);
        webCrawler.setScraper(scraperDummy);

        // act
        webCrawler.changeType(newType);

        // assert
        verify(statisticDummy).changeTargetType(newType);
        verify(statisticDummy).resetData();
    }

    @Test
    @Parameters(method = "testSampleWebForCrawling")
    public void theSearchDepthLevelShouldBeIncreasedWhenANewRecursiveCrawlStart(URL initURl) throws IOException {
        // arrange
        WebCrawler webCrawler = new WebCrawler(initURl, validKeyword, validGeneralItemType);
        webCrawler.setStatistic(statisticDummy);

        webCrawler.setScraper(scraperDummy);
        when(scraperDummy.scrapeAndGetItem(any())).thenReturn(null);

        // act
        webCrawler.startCrawler();

        // assert
        verify(statisticDummy, atLeast(1)).increaseSearchDepth();
    }
}