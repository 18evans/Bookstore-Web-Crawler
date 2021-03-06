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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    public Object[] testChangeKeywords(){
        return new Object[]{
                new Object[] { new Music(), "Let It Go", "Human"},
                new Object[] { new Books(), "Head First - Design Pattern", "Practical Unit Testing with JUnit"},
                new Object[] { new Movies(), "Interstellar", "The Matrix"}
        };
    }

    public Object[] testChangeTypes(){
        return new Object[]{
                new Object[] { new Music(), "Let It Go", new Books()},
                new Object[] { new Books(), "Head First - Design Pattern", new Movies()},
                new Object[] { new Movies(), "Interstellar", new Music()}
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
        assertNotNull("The object was not instantiated correctly!!", statistic);
    }

    /***
     * A constructor test which is used to verify whether the private field
     * "startTime" is set to the current date time when the "Statistic" object
     * is instantiated.
     */
    @Test
    public void afterInstantiationTheStartTimeShouldEqualNow(){
        // arrange
        Long expectedTime = System.currentTimeMillis();
        dummySUTBook = new Statistic(dummyBook, dummyKeyword, expectedTime);

        // act
        Long actualTime = dummySUTBook.getStartTime();

        // assert
        assertEquals("The start time did not match!!", expectedTime, actualTime);
        assertNotNull("The start time was null", actualTime);
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
        assertNotNull("The total number of pages explored was instantiated with null value!!", actualNrOfPageExplored);
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
        assertNotNull("The search depth level was null when the object was instantiated.!!", actualSearchDepthLevel);
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
    @Parameters(method = "testChangeKeywords")
    public void afterChangeKeywordTheNewKeywordShouldMatch(Item type, String oldKeyword, String expectedNewKeyword){
        // arrange
        Statistic sut = new Statistic(type, oldKeyword);

        // act
        sut.changeKeyword(expectedNewKeyword);
        String actualKeyword = sut.getKeyword();

        // assert
        assertEquals("The keyword was not changed!!", expectedNewKeyword, actualKeyword);
        assertNotNull("The keyword was null!!", actualKeyword);
    }

    /***
     * A test which is used to verify that the new target format type should
     * be changed into new type after the type has been changed in the crawling
     * process.
     * This is a parameterized test where the testy can try different values of
     * Item classes to check if the new values are as expected ones.
     */
    @Test
    @Parameters(method = "testChangeTypes")
    public void afterChangeTargetTypeTheNewTypeShouldMatch(Item oldType, String keyword, Item expectedNewType){
        // arrange
        Statistic sut = new Statistic(oldType, keyword);

        // act
        sut.changeTargetType(expectedNewType);
        Item actualNewType = sut.getType();

        // assert
        assertEquals("The new search type was not changed correctly!!", expectedNewType, actualNewType);
        assertNotNull("The new search type was changed to null!!", actualNewType);
    }

}