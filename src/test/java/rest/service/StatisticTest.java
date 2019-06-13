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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class StatisticTest {
    private Item dummyBook, dummyMovie, dummyMusic;
    private Statistic dummySUTBook, dummySUTMovie, dummySUTMusic;
    private String dummyKeyword;

    @Before
    public void setUp(){
        dummyBook = mock(Books.class);
        dummyMovie = mock(Movies.class);
        dummyMusic = mock(Music.class);
        dummyKeyword = "dummy keyword";
        dummySUTBook = new Statistic(dummyBook, dummyKeyword);
        dummySUTMovie = new Statistic(dummyMovie, dummyKeyword);
        dummySUTMusic = new Statistic(dummyMovie, dummyKeyword);
    }

    public Object[] testFields(){
        return new Object[]{
                new Object[] { new Music(), "Let It Go"},
                new Object[] { new Books(), "Head First - Design Pattern"},
                new Object[] { new Movies(), "Interstellar"}
        };
    }


    /***
     * A constructor test which is used to check whether the parameters
     * are in correct state and are what are expected.
     * This is a parameterized test where tester can try different value
     * for the Item classes and keyword
     */
    @Test
    @Parameters(method = "testFields")
    public void afterInstantiationFieldsShouldBeSet(Item expectedItemType, String expectedKeyword){
        // arrange
        Statistic statistic = new Statistic(expectedItemType, expectedKeyword);

        // act
        Item actualItem = statistic.getType();
        String actualKeyword = statistic.getKeyword();

        // assert
        assertEquals("The expected item type did not match!!", expectedItemType, actualItem);
        assertEquals("The expected item class type did not match!!!", expectedItemType.getClass(), actualItem.getClass());
        assertEquals("The expected keyword did not match!!", expectedKeyword, actualKeyword);
    }

    /***
     * A constructor test which is used to verify whether the private field
     * "startTime" is set to the current date time when the "Statistic" object
     * is instantiated.
     */
    @Test
    public void afterInstantiationTheStartTimeShouldEqualNow(){
        // arrange

        // act
        Long expectedTime = System.currentTimeMillis();
        Long actualTime = dummySUTBook.getStartTime();

        // assert
        assertEquals("The start time did not match!!", expectedTime, actualTime);
    }

    /***
     * A constructor test which is used to verify that the total number of pages
     * explored should equal to Zero after instantiation.
     */
    @Test
    @Parameters(method = "testFields")
    public void afterInstantiationTheNrOfPagesExploredShouldBeZero(Item type, String dummyKeyword){
        // arrange
        Integer expectedNrOfPageExplored = 0;
        Statistic sut = new Statistic(type, dummyKeyword);

        // act
        Integer actualNrOfPageExplored = sut.getPagesExplored();

        // assert
        assertEquals("The total number of pages explored when the object was initialized was not zero!", expectedNrOfPageExplored, actualNrOfPageExplored);
    }

    /***
     * A constructor test which is used to verify that the search depth level
     * should equal to Zero after instantiation
     */
    @Test
    @Parameters(method = "testFields")
    public void afterInstantiationTheSearchDepthShouldBeZero(Item type, String keyword){
        // arrange
        Integer expectedSearchDepthLevel = 0;
        Statistic sut = new Statistic(type, keyword);

        // act
        Integer actualSearchDepthLevel = sut.getSearchDepth();

        // assert
        assertEquals("The search depth level when the object was initialized was not zero!!", expectedSearchDepthLevel, actualSearchDepthLevel);
    }


    /***
     * A test which is used to verify that when the method "increasePagesExplored"
     * is called, the total number of pages explored should only be increased by one.
     * The total number of pages explored should only be increased after the a page
     * is fully explored.
     */
    @Test
    @Parameters(method = "testFields")
    public void afterInvokeMethodTheTotalNumberOfPagesExploredShouldBeIncreasedByOne(Item type, String keyword){
        // arrange
        Integer expectedNrOfPagesExplored = 1;
        Statistic sut = new Statistic(type, keyword);

        // act
        sut.increasePagesExplored();
        Integer actualNrOfPagesExplored = sut.getPagesExplored();

        // assert
        assertEquals("The number of total pages explored was not increased by one!!", expectedNrOfPagesExplored, actualNrOfPagesExplored);
    }

    /***
     * A test which is used to verify that when the method "increaseSearchDepth"
     * is called, the search depth level should be increased by one.
     * The search depth should only be increased if and only if the new explored page
     * is not at the same level.
     * E.g.: Web A contains hyperlinks for web B and C. If the web crawler first explores
     * web B then the depth level will be increase. However, exploring web C will not
     * increase the depth level
     */
    @Test
    @Parameters(method = "testFields")
    public void afterInvokeMethodAndTheNewLevelIsDeeperTheSearchDepthShouldBeIncreasedByOne(Item type, String keyword){
        // arrange
        Integer expectedSearchDepth = 1;
        Statistic sut = new Statistic(type, keyword);

        // act
        sut.increaseSearchDepth();
        Integer actualSearchDepth = sut.getSearchDepth();

        // assert
        assertEquals("The search depth was not increased by one!!", expectedSearchDepth, actualSearchDepth);
    }

    /***
     * A test which is used to verify that the new target keyword should be
     * changed into new keyword after the keyword has been changed the crawling
     * process.
     * This is a parameterized test where the tester can try different values
     * of keyword to check if the new values are as expected ones.
     */
    @Test
    //@Parameters(method = "")
    public void afterChangeKeywordTheNewKeywordShouldMatch(){

    }

    /***
     * A test which is used to verify that the new target format type should
     * be changed into new type after the type has been changed in the crawling
     * process.
     * This is a parameterized test where the testy can try different values of
     * Item classes to check if the new values are as expected ones.
     */
    @Test
    //@Parameters(method = "")
    public void afterChangeTargetTypeTheNewTypeShouldMatch(){

    }

    /***
     * A test which is used to verify generating Json behavior.
     * The test expected all the keys and values are in correct order
     * and the method does not return null
     */
    @Test
    public void generateJsonShouldReturnJsonObjectWithCorrectValues(){

    }


}