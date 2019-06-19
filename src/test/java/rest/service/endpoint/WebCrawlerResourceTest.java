package rest.service.endpoint;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import rest.service.WebCrawlerResponse;
import rest.service.model.Books;
import rest.service.model.Item;
import rest.service.model.Movies;
import rest.service.model.Music;

import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static rest.service.endpoint.WebCrawlerResource.MSG_ERROR_TYPE_INVALID;
import static rest.service.endpoint.WebCrawlerResource.MSG_ERROR_URL_INVALID;
import static rest.service.endpoint.WebCrawlerResource.MSG_ERROR_URL_NULL_EMPTY_WHITESPACE;

@RunWith(JUnitParamsRunner.class)
public class WebCrawlerResourceTest {

    //returns json
    private final WebCrawlerResource resource = new WebCrawlerResource(); //SUT

    //example valid argument variables
    private static URL exampleValidDashboardUrl;
    private static URL exampleValidMovieUrl;
    private static final String validKeyword = "sample keyword";
    private static final Class<? extends Item> validBookType = Books.class;
    private static final Class<? extends Item> validMoviesType = Movies.class;
    private static final Class<? extends Item> validMusicType = Music.class;
    private static final String exampleKeyword = "The Bible";

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        exampleValidDashboardUrl = new URL("http://i367506.hera.fhict.nl/webcrawl_example/catalog.php");
        exampleValidMovieUrl = new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=201");
    }

    /**
     * Check if SUT on {@link WebCrawlerResource#getContent(String, String, String)}
     * returns an instance of {@link Response} class object.
     */
    @Test
    public void sutReturnsResponseReturnType() {
        //arrange - uses the valid global constants

        //act
        Response response = resource.getContent("", "", "");

        //assert
        assertNotNull("Response was NULL", response);
        assertThat("Response was NOT an instance of Response class.",
                response, instanceOf(Response.class));
    }

    /**
     * Check that the returned object wrapped within the {@link Response#getEntity()}
     * is an instance of {@link WebCrawlerResponse}.
     */
    @Test
    public void sutReturnsWebCrawlerResponseWithinResponse() {
        //arrange - use example valid URL

        //act
        Response response = resource.getContent(exampleValidDashboardUrl.toString(), "", "");

        //assert
        assertThat("Object entity within Response was NOT an instance of WebCrawlerResponse class.",
                response.getEntity(), instanceOf(WebCrawlerResponse.class));
    }

    /**
     * Check that response is a Server Error with the correct expected message of a type to look for is passed
     * as argument that is unsupported yet.
     */
    @Test
    public void sutResponseReturnsInvalidTypeServerErrorIfPassedTypeIsNotSupported() {
        //arrange - use example valid URL
        String unsupportedType = "Cars";

        //act
        Response response = resource.getContent(exampleValidDashboardUrl.toString(), unsupportedType, "");

        //assert
        assertEquals("Response on invalid type was NOT server error as was expected.",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                response.getStatus());
        assertEquals("Server Error did not in fact carry the expected entity error String message.",
                MSG_ERROR_TYPE_INVALID,
                response.getEntity());
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
     * <p>
     * Note: Cannot Mock final classes ({@link URL} and {@link String}) thus using parameterized test
     * for each of the three scenarios
     *
     * @param urlAsNullEmptyOrWhiteSpace - url equal to either NULL, "" or "    ".
     */
    @Test
    @Parameters(method = "getNullEmptyOrWhiteSpaceStrings")
    public void responseReturnsUrlNullEmptyWhiteSpaceServerErrorIfUrlIsNullEmptyOrWhiteSpace(String urlAsNullEmptyOrWhiteSpace) {
        //arrange - url from Parameterized method, type & keyword from Global constants

        //act
        Response responseNull = resource.getContent(urlAsNullEmptyOrWhiteSpace, "", "");

        //assert
        assertEquals("Response on NULL url was NOT server error as was expected.",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                responseNull.getStatus());
        assertEquals("Server Error did not in fact carry the expected entity error String message.",
                MSG_ERROR_URL_NULL_EMPTY_WHITESPACE,
                responseNull.getEntity());
    }

    private static URL[] getValidUrls() throws MalformedURLException {
        return new URL[]{
                new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=101"),
                new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=201"),
                new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=301")
        };
    }

    /**
     * Method asserts that a Valid URL, Type and keyword passed as argument will cause endpoint
     * to NOT return a Server error.
     * Method uses Parameterized Tests to get valid URL argument.
     *
     * @param url - the valid URL.
     */
    @Test
    @Parameters(method = "getValidUrls")
    public void responseDoesNotReturnServerErrorOnValidArguments(URL url) {
        //arrange - uses the valid global constants

        //act
        Response response = resource.getContent(url.toString(), validBookType.getSimpleName(), exampleKeyword);

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
        Response response = resource.getContent(url, validBookType.getSimpleName(), exampleKeyword);

        //assert
        assertEquals("Response did in fact NOT return a SERVER ERROR when it was expected.",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                response.getStatus());
        assertEquals("Server Error did not in fact carry the expected entity error String message.",
                MSG_ERROR_URL_INVALID,
                response.getEntity());
    }

    /**
     * Test case checks that if a URL is specified, response succeeds
     * Example:     getContent("wikipedia.org") returns OK response.
     */
    @Test
    public void responseReturnsOKIfOnlyURLIsSpecified() {
        //arrange - use example valid url + null passed vars

        //act
        Response response = resource.getContent(exampleValidDashboardUrl.toString(), "", "");

        //assert
        assertEquals("Response did NOT return OK when it was expected.",
                Response.Status.OK.getStatusCode(),
                response.getStatus());
    }

    /**
     * Test case checks that response returns whole-site-craw array.
     * Included will be Statistics object and Result objects.
     * Test case checks that response returns several arrays, one for each
     * model type. Currently there are 3 supported model types, meaining 3 arrays
     * are expected.
     * <p>
     * IMPORTANT: Expected results are to have 1 or more of each supported type.
     */
    @Test
    public void responseWithOnlyAURLSpecifiedReturnsWholeSiteCrawlWithMultipleSetsWithMoreThan0Elements() {
        //arrange - using dashboard url to find all items

        //act
        final Response response = resource.getContent(exampleValidDashboardUrl.toString(), "", "");
        final WebCrawlerResponse webCrawlerResponse = (WebCrawlerResponse) response.getEntity();
        final Set<Books> books = webCrawlerResponse.getBooks();
        final Set<Movies> movies = webCrawlerResponse.getMovies();
        final Set<Music> music = webCrawlerResponse.getMusic();

        //assert
        assertThat(books.size(), greaterThan(0));
        assertThat(movies.size(), greaterThan(0));
        assertThat(music.size(), greaterThan(0));
    }

    private static Class<? extends Item>[] getValidTypes() {
        return new Class[]{
                Books.class,
                Movies.class,
                Music.class
        };
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
     * @param type type query param - Compared to the response's array's object type.
     */
    @Test
    @Parameters(method = "getValidTypes")
    public void responseReturnsOnlyOneArrayOfObjectTypeSameAsTheOneSpecifiedInTheQueryParam(Class<? extends Item> type) {
        //arrange - using dashboard url to find all items

        //act
        final Response response = resource.getContent(exampleValidDashboardUrl.toString(), type.getSimpleName(), "");
        final WebCrawlerResponse webCrawlerResponse = (WebCrawlerResponse) response.getEntity();
        final Set<Books> books = webCrawlerResponse.getBooks();
        final Set<Movies> movies = webCrawlerResponse.getMovies();
        final Set<Music> music = webCrawlerResponse.getMusic();

        //assert

        if (type.equals(Books.class)) {
            assertThat(books.size(), greaterThan(0));
            assertTrue(movies.isEmpty());
            assertTrue(music.isEmpty());
        } else if (type.equals(Movies.class)) {
            assertTrue(books.isEmpty());
            assertTrue(music.isEmpty());
            assertThat(movies.size(), greaterThan(0));
        } else if (type.equals(Music.class)) {
            assertTrue(movies.isEmpty());
            assertTrue(books.isEmpty());
            assertThat(music.size(), greaterThan(0));
        }
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

    @Test
    @Parameters(method = "getValidTypes")
    public void webCrawlResponseHasStatisticWithTheOriginalPassedTypeAndKeyword(Class<? extends Item> classType) {
        //arrange
        String randomKeyword = RandomStringUtils.randomAlphabetic(10); //generate random keyword at 10 character long
        String type = classType.getSimpleName(); //get type as string name

        //act
        final Response endpointResponse = resource.getContent(exampleValidDashboardUrl.toString(), type, randomKeyword);
        final WebCrawlerResponse webCrawlerResponse = (WebCrawlerResponse) endpointResponse.getEntity();
//        JSONObject jsonObject = new JSONObject(response.getEntity());

        //assert
        assertEquals(webCrawlerResponse.getStatistic().getType().getClass().getSimpleName(), type);
        assertEquals(webCrawlerResponse.getStatistic().getKeyword(), randomKeyword);
    }

}