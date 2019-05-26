package rest.service;

import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticTest {

    @Before
    public void setUp(){

    }

    /***
     * A constructor test which is used to check whether the parameters
     * are in correct state and are what are expected.
     * This is a parameterized test where tester can try different value
     * for the Item classes and keyword
     */
    @Test
    @Parameters(method = "")
    public void afterInstantiationFieldsShouldBeSet(){

    }

    /***
     * A constructor test which is used to verify whether the private field
     * "startTime" is set to the current date time when the "Statistic" object
     * is instantiated.
     */
    @Test
    public void afterInstantiationTheStartTimeShouldEqualNow(){

    }

    /***
     * A constructor test which is used to verify that the total number of pages
     * explored should equal to Zero after instantiation.
     */
    @Test
    public void afterInstantiationTheNrOfPagesExploredShouldBeZero(){

    }

    /***
     * A constructor test which is used to verify that the search depth level
     * should equal to Zero after instantiation
     */
    @Test
    public void afterInstantiationTheSearchDepthShouldBeZero(){

    }


    /***
     * A test which is used to verify that when the method "increasePagesExplored"
     * is called, the total number of pages explored should only be increased by one.
     * The total number of pages explored should only be increased after the a page
     * is fully explored.
     */
    @Test
    public void afterInvokeMethodTheTotalNumberOfPagesExploredShouldBeIncreasedByOne(){

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
    public void afterInvokeMethodAndTheNewLevelIsDeeperTheSearchDepthShouldBeIncreasedByOne(){

    }

    /***
     * A test which is used to verify that the new target keyword should be
     * changed into new keyword after the keyword has been changed the crawling
     * process.
     * This is a parameterized test where the tester can try different values
     * of keyword to check if the new values are as expected ones.
     */
    @Test
    @Parameters(method = "")
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
    @Parameters(method = "")
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