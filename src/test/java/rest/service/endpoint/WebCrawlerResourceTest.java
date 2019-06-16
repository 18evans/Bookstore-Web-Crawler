package rest.service.endpoint;

import junitparams.JUnitParamsRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import rest.service.model.Books;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class WebCrawlerResourceTest {

    //1 returns json
    private final WebCrawlerResource resource = new WebCrawlerResource(); //SUT

    @Test
    public void sutReturnsResponseReturnType() {
        //arrange
        final String url = "https://en.wikipedia.org/wiki/Books";
        final String type = Books.class.getSimpleName();
        final String keyword = "The Bible";

        //act
        Response response = resource.getContent(url, type, keyword);

        //assert
        assertNotNull("Response was NULL", response);
        assertThat("Response was NOT an instance of Response class.",
                response, instanceOf(Response.class));
    }

    //tests with only 1 parameter

    /**
     * If query param value for "url" is passed as null, empty String or white space.
     * Example:     getContent(null) returns Server Error.
     */
    @Test
    @Ignore
    public void responseReturnsErrorIfURLIsNullEmptyOrWhitespace() {
    }

    /**
     * /**
     * Test case checks that response returns Error if URL is omitted,
     * when type is present.
     */
    @Test
    @Ignore
    public void responseReturnsErrorIfURLIsNotSpecifiedButTypeIs() {
    }

    /**
     * Test case checks that response returns Error if URL is omitted,
     * when keyword is present.
     */
    @Test
    @Ignore
    public void responseReturnsErrorIfUrlIsNotSpecifiedButKeywordIs() {
    }

    /**
     * Test case checks that if a URL is specified, response succeeds
     * Example:     getContent("wikipedia.org") returns OK response.
     */
    @Test
    @Ignore
    public void responseReturnsOKIfURLIsSpecified() {
    }

    /**
     * Test case checks that if a URL is specified, response returns
     * a value for "time_elapsed".
     * Example:     getContent("wikipedia.org") contains
     * {
     * "time": 111111
     * }.
     */
    @Test
    @Ignore
    public void ifResponseReturnsOKItContainsValueForTimeElapsed() {
    }

    /**
     * Test case checks that response returns whole-site-craw array.
     * Included will be value for "pags_explored", "search_depth".
     * Example:     getContent("wikipedia.org") returns OK response.
     */
    @Test
    @Ignore
    public void responseWithOnlyAURLSpecifiedReturnsWholeSiteCrawl() {
    }

    /**
     * Test case checks that response returns several arrays, one for each
     * model type. Currently there are 3 supported model types, meaining 3 arrays
     * are expected.
     * Example:     getContent("wikipedia.org") has 3 arrays, one for each model type.
     */
    @Test
    @Ignore
    public void responseReturnsMultipleArraysOfEachModelTypeIfURLIsSpecified() {
    }

    //tests with 2 parameters:
    //  url + type

    /**
     * Test case checks that response returns error if specified "type" query
     * param doesn't exist.
     * Example:     getContent("wikipedia.org", "dfgdsfsf") returns
     * reponse with error response code.
     */
    @Test
    @Ignore
    public void responseReturnsErrorIfTypeDoesNotExistInModels() {
    }

    /**
     * If response has a URL specified and a type which is null, empty string or white space
     * response returned is a web-site crawl. Does not contain key matching "result"
     * Example:     getContent("wikipedia.org", null)  result returns same JSON key format as
     * getContent("wikipedia.org")
     */
    @Test
    @Ignore
    public void responseWithNullEmptyOrWhitespaceTypeReturnsSameFormatAsResponseWithOnlyURLSpecified() {
    }

    /**
     * Test case will check that the "type" query param specified
     * will be the actual type of the array elements in the Response.
     * No other type of array will be contained in the Response array.
     * Test case is a parameterized test where in annotation "@Parameters(method = "")"
     * specified is a local method which returns array of simulated objects of valid query param combination
     * where each element has an existing URL, and a type that actually exists as a model.
     * Needs test class to have annotation "@RunWith(JUnitParamsRunner.class)"
     * Example:     getContent("wikipedia.org", "Book") returns response
     * with only one array which contains Book elements.
     *
     * @param urlAsString url query param - Required to be present for response to not return error.
     * @param type        type query param - Compared to the response's array's object type.
     */
    @Test
    @Ignore
//    @Parameters(method = "")
    public void responseReturnsOnlyOneArrayOfObjectTypeSameAsTheOneSpecifiedInTheQueryParam(String urlAsString,
                                                                                            String type) {
    }

    //  url + keyword

    /**
     * If response has a URL specified and a keyword which is null, empty string or white space
     * response returned is a web-site crawl. Does not contain key matching "result"
     * Example:     getContent("wikipedia.org", "Book", null)  result returns same JSON key format as
     * getContent("wikipedia.org")
     */
    @Test
    @Ignore
    public void responseWithNullEmptyOrWhitespaceKeywordReturnsSameFormatAsResponseWithOnlyURLSpecified() {
    }

    /**
     * If response has a URL specified and a keyword it will contain
     * a value for "result"
     * Example:
     * {
     * "id": 4,
     * "time":1499696751,
     * "result":{}
     * }
     */
    @Test
    @Ignore
    public void responseReturnsArrayResultIfAKeyWordIsSpecified() {
    }

    //3 params - url + type + keyword

    /**
     * If response has a URL and a type specified, keyword it will contain a value for "result".
     * Example:
     * {
     * "id": 4,
     * "time":1499696751,
     * "result":{}
     * }
     */
    @Test
    @Ignore
    public void responseWithValidTypeAndValidKeywordContainsKeyForResult() {
    }

}