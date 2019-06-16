package rest.service;

import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import rest.service.model.Books;
import rest.service.model.Item;
import rest.service.model.Movies;
import rest.service.model.Music;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WebCrawlerTest {
    private String emptyString;
    private String nullString;
    private String validKeyword;
    private Item validBookType, validMovieType, validMusicType;
    private Item validGeneralItemType;
    private URL validUrl;

    @Before
    public void setUp() throws MalformedURLException {
        emptyString = "";
        nullString = null;
        validKeyword = "sample keyword";
        validBookType = mock(Books.class);
        validMovieType = mock(Movies.class);
        validMusicType = mock(Music.class);
        validGeneralItemType = mock(Item.class);
        validUrl = new URL("https://fontys.nl");
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
    public void afterInstantiationAllFieldsShouldBeSet(){
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
    public void afterInstantiationShouldHaveOnlyOneInitUrl(){
        // arrange
        WebCrawler webCrawler = new WebCrawler(validUrl, validKeyword, validGeneralItemType);
        Integer expectedNrOfUrl = 1;
        String expectedUrlString = validUrl.toString();

        // act
        Integer actualNrOfUrl = webCrawler.getUrlList().size();
        String actualUrlString = webCrawler.getUrlList().toArray()[0].toString();

        // assert
        assertEquals("The number of initial url was not 1", actualNrOfUrl, expectedNrOfUrl);
    }

//    /***
//     * If the current collection of urls already empty, but found more hyperlinks
//     * the process should add new hyperlinks to to the current collection of urls
//     * and continue crawling.
//     * @param urls - the current collection of URLs
//     */
//    @Test
//    @Parameters(method = "")
//    public void ifTheUrlSetIsEmptyButFoundMoreLinksTheProcessShouldContinueWithNewLinksAddedToTheURLSet(Set<URL> urls) {
//
//    }
//
//    /***
//     * When the crawling process could not find anything and the new url set to be explored
//     * are empty. The method should return an empty collection.
//     * @param urls - the current collection of URLs
//     */
//    @Test
//    @Parameters(method = "")
//    public void ifTheUrlSetIsEmptyButNotFoundAnythingShouldEmptyCollection(Set<URL> urls) {
//
//    }
//
//    /***
//     * When the crawling process found several items and the current collection of URLs is empty,
//     * the method should return the collection of found items.
//     * @param urls - the current collection of URLs
//     */
//    @Test
//    @Parameters(method = "")
//    public void ifTheUrlSetIsEmptyButFoundSomeItemsShouldReturnCollectionOfThatItems(Set<URL> urls) {
//
//    }
//
//    /***
//     * During the crawling process, if the current collection of URLs are not empty
//     * and no result found yet, the crawling should continue to the next URL in the
//     * collection of URLs
//     * @param urls - the current collection of URLs
//     */
//    @Test
//    @Parameters(method = "")
//    public void ifTheUrlSetIsNotEmptyButNotFoundAnythingShouldContinueWithTheNextUrl(Set<URL> urls) {
//
//    }
//
//    /***
//     * During the crawling process, if found an item that match the searching criteria
//     * , the process should stop immediately and return that item.
//     * @param urls - the current collection of URLs
//     */
//    @Test
//    @Parameters(method = "")
//    public void ifTheUrlSetIsNotEmptyButFoundTheItemThatMatchExactlyShouldReturnACollectionWithOnlyThatItem(Set<URL> urls) {
//
//    }
//
//    /***
//     * During the crawling process, if the current collection of URLs is empty but
//     * already found several close matched items, the method should return a collection
//     * of close matched items
//     * @param url
//     */
//    @Test
//    @Parameters(method = "")
//    public void ifTheUrlSetIsEmptyButFoundSomeCloseMatchItemsShouldReturnACollectionOfCloseMatchedItems(Set<URL> url) {
//
//    }
}