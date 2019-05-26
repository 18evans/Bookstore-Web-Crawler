package rest.service;

import junitparams.Parameters;
import org.junit.Test;

import java.net.URL;
import java.util.Set;

import static org.junit.Assert.*;

public class WebCrawlerTest {

//    /***
//     * The test that verify the method should throw an IllegalArgumentException when
//     * the given URL is Empty or Null.
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void nullOrEmptyURLShouldThrowIllegalArgumentException(){
//
//    }
//
//    /***
//     * The test that verify the method should not throw an IllegalArgumentException when
//     * the given URL is in a valid format.
//     */
//    @Test
//    public void validURLShouldNotThrowException(){
//
//    }
//
//    /***
//     * The test that verify the method should throw an IllegalArgumentException when
//     * the given keyword is Empty or Null.
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void nullOrEmptyKeywordShouldThrowIllegalArgumentException(){
//
//    }
//
//    /***
//     * The test the test that verify the method should not throw an IllegalArgumentException when
//     * the given keyword is in a valid format
//     */
//    @Test
//    public void validKeywordShouldNotThrowIllegalArgumentException(){
//
//    }

    /***
     * If the given type is not a class that derived from Item class
     * , an IllegalArgumentException should be throw
     */
    @Test(expected = IllegalArgumentException.class)
    public void ifTheTypeIsNotItemShouldThrowException(){

    }



    /***
     * If the current collection of urls already empty, but found more hyperlinks
     * the process should add new hyperlinks to to the current collection of urls
     * and continue crawling.
     * @param urls - the current collection of URLs
     */
    @Test
    @Parameters(method = "")
    public void ifTheUrlSetIsEmptyButFoundMoreLinksTheProcessShouldContinueWithNewLinksAddedToTheURLSet(Set<URL> urls){

    }

    /***
     * When the crawling process could not find anything and the new url set to be explored
     * are empty. The method should return an empty collection.
     * @param urls - the current collection of URLs
     */
    @Test
    @Parameters(method = "")
    public void ifTheUrlSetIsEmptyButNotFoundAnythingShouldReturnNull(Set<URL> urls){

    }

    /***
     * When the crawling process found several items and the current collection of URLs is empty,
     * the method should return the collection of found items.
     * @param urls - the current collection of URLs
     */
    @Test
    @Parameters(method = "")
    public void ifTheUrlSetIsEmptyButFoundSomeItemsShouldReturnCollectionOfThatItems(Set<URL> urls){

    }

    /***
     * During the crawling process, if the current collection of URLs are not empty
     * and no result found yet, the crawling should continue to the next URL in the
     * collection of URLs
     * @param urls - the current collection of URLs
     */
    @Test
    @Parameters(method = "")
    public void ifTheUrlSetIsNotEmptyButNotFoundAnythingShouldContinueWithTheNextUrl(Set<URL> urls){

    }

    /***
     * During the crawling process, if found an item that match the searching criteria
     * , the process should stop immediately and return that item.
     * @param urls - the current collection of URLs
     */
    @Test
    @Parameters(method = "")
    public void ifTheUrlSetIsNotEmptyButFoundTheItemThatMatchExactlyShouldReturnACollectionWithOnlyThatItem(Set<URL> urls){

    }

    /***
     * During the crawling process, if the current collection of URLs is empty but
     * already found several close matched items, the method should return a collection
     * of close matched items
     * @param url
     */
    @Test
    @Parameters(method = "")
    public void ifTheUrlSetIsEmptyButFoundSomeCloseMatchItemsShouldReturnACollectionOfCloseMatchedItems(Set<URL> url){

    }
}