package rest.service.endpoint;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import rest.service.model.Books;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static rest.service.endpoint.WebCrawlerResource.MSG_ERROR_URL_INVALID;
import static rest.service.endpoint.WebCrawlerResource.MSG_ERROR_URL_NULL_EMPTY_WHITESPACE;

@RunWith(JUnitParamsRunner.class)
public class WebCrawlerResourceTest {

    //returns json
    private final WebCrawlerResource resource = new WebCrawlerResource(); //SUT

    //example valid argument variables
    private final String url = "https://en.wikipedia.org/wiki/Books";
    private final String type = Books.class.getSimpleName();
    private final String keyword = "The Bible";

    @Test
    public void sutReturnsResponseReturnType() {
        //arrange - uses the valid global constants

        //act
        Response response = resource.getContent(url, type, keyword);

        //assert
        assertNotNull("Response was NULL", response);
        assertThat("Response was NOT an instance of Response class.",
                response, instanceOf(Response.class));
    }


    //tests of only 1 parameter

    private static String[] getNullEmptyOrWhiteSpaceStrings() {
        return new String[]{
                null,
                "",
                "     "
        };
    }

    /**
     * If query param value for "url" is a passed NULL pointer, Empty String or String of only White Space,
     * expected return object is a Response carrying ServerError entity with
     * a Server error entity message for NULL pointer, Empty String or String of only White Space URLs.
     * Example:      getContent(null),
     * getContent("")
     * and getContent("     ")
     * return Server Error.
     * @param urlAsNullEmptyOrWhiteSpace - url equal to either NULL, "" or "    ".
     */
    @Test
    @Parameters(method = "getNullEmptyOrWhiteSpaceStrings")
    public void responseReturnsServerErrorIfUrlIsNullEmptyOrWhiteSpace(String urlAsNullEmptyOrWhiteSpace) {
        //arrange - url from Parameterized method, type & keyword from Global constants

        //act
        Response responseNull = resource.getContent(urlAsNullEmptyOrWhiteSpace, type, keyword);

        //assert
        assertEquals("Response on NULL url was NOT server error as was expected.",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                responseNull.getStatus());
        assertEquals("Server Error did not in fact carry the expected entity error String message.",
                MSG_ERROR_URL_NULL_EMPTY_WHITESPACE,
                responseNull.getEntity());
    }

    private static String[] getValidUrls() {
        return new String[]{
                "https://en.wikipedia.org/wiki/Books",
                "https://en.wikipedia.org/wiki/Film",
                "https://en.wikipedia.org/wiki/Music"
        };
    }

    /**
     * Method asserts that a Valid URL passed as argument will cause endpoint
     * to NOT return a Server error.
     * Method uses Parameterized Tests to get valid URL argument.
     *
     * @param url - the valid URL.
     */
    @Test
    @Parameters(method = "getValidUrls")
    public void responseDoesNotReturnServerErrorOnValidatedURL(String url) {
        //arrange - uses the valid global constants

        //act
        Response response = resource.getContent(url, type, keyword);

        //assert
        assertNotEquals("Response did in fact return a SERVER ERROR when it was not expected.",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                response.getStatus());
    }

    private static String[] getInvalidUrls() {
        return new String[]{
                "asdf",
                "fdgdfhg.gfddsfgdg",
                "gfdhfnb.dsfgdg",
                "www.dfgdfgdfgdfgdfgdfgdfg.hngfdjhgjgfhjgdhj"
        };
    }

    /**
     * Method asserts that an Invalid URL passed as argument will cause endpoint
     * to return Server error.
     * Method uses Parameterized Tests to get an invalid URL argument.
     *
     * @param url - the invalid URL.
     */
    @Test
    @Parameters(method = "getInvalidUrls")
    public void responseReturnsServerErrorOnInvalidURL(String url) {
        //arrange - uses the valid global constants

        //act
        Response response = resource.getContent(url, type, keyword);

        //assert
        assertEquals("Response did in fact NOT return a SERVER ERROR when it was expected.",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                response.getStatus());
        assertEquals("Server Error did not in fact carry the expected entity error String message.",
                MSG_ERROR_URL_INVALID,
                response.getEntity());
    }

    //todo test case empty type
    //todo test case empty keyword
    
    /**
     * Test case checks that if a URL is specified, response succeeds
     * Example:     getContent("wikipedia.org") returns OK response.
     */
    @Test
    public void responseReturnsOKIfOnlyURLIsSpecified() {
        //arrange - use example valid url + null passed vars

        //act
        Response response = resource.getContent(url, "", "");

        //assert
        assertEquals("Response did NOT return OK when it was expected.",
                Response.Status.OK.getStatusCode(),
                response.getStatus());
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