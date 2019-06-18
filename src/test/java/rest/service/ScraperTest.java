package rest.service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import rest.service.model.Books;
import rest.service.model.Item;
import rest.service.model.Movies;
import rest.service.model.Music;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(JUnitParamsRunner.class)
public class ScraperTest {

    private final Scraper scraper = new Scraper(); //SUT
    /**
     * Since URL is pointing to FHICT self service Web host required is Cisco VPN to connect.
     */
    private static URL exampleValidBooksUrl;
    private static URL exampleValidMovieUrl;

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        exampleValidBooksUrl = new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=101");
        exampleValidMovieUrl = new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=201");
    }

    /**
     * If Jsoup {@link Document} object doesn't point to page of item,
     * returned will be null instance of Item.
     */
    @Test
    public void ifPageIsNotOfAValidItemDoNotReturnNull() {
        //arrange
        Document document = mock(Document.class);
        when(document.body()).thenReturn(null); //document points to null page

        //act
        Item result = scraper.scrapeAndGetItem(document);

        //assert
        assertNull("Scraped result was NOT NULL when it was expected to be.",
                result);
    }

    /**
     * If Jsoup {@link Document} object points to a page of a supported item category,
     * returned will be  be null instance of Item.
     */
    @Test
    public void ifPageIsAValidItemPageDoNotReturnNull() throws IOException {
        //arrange - document point to valid webpage of an item
        Document document = Jsoup.connect(exampleValidMovieUrl.toString()).timeout(6000).get();

        //act
        Item result = scraper.scrapeAndGetItem(document);

        //assert
        assertNotNull("Scraped result was NULL when it was expected to not be.",
                result);
    }


    private static Map.Entry<Class<? extends Item>, String>[] getValidTypeWithValidUrl() {
        Map.Entry<Class<? extends Item>, String>[] entries = new Map.Entry[]{
                new AbstractMap.SimpleEntry<>(Books.class, "http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=101"),
                new AbstractMap.SimpleEntry<>(Movies.class, "http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=201"),
                new AbstractMap.SimpleEntry<>(Music.class, "http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=301")
        };
        return entries;
    }

    /**
     * The class of the returned scraped Item object instance must be in name equal to the type of
     * the Item category on the webpage.
     */
    @Test
    @Parameters(method = "getValidTypeWithValidUrl")
    public void onScrapeOfItemPageReturnedShouldBeClassObjectOfSameName(Map.Entry<Class<? extends Item>, String> entry) throws IOException {
        //arrange
        final Class<? extends Item> classnameItem = entry.getKey();
        final String url = entry.getValue();
        Document document = Jsoup.connect(url).timeout(6000).get(); //document point to valid webpage of an item

        //act
        Item result = scraper.scrapeAndGetItem(document);

        //assert
        assertEquals("The Scraped object was not an instance of the class it was expected to be.",
                classnameItem, result.getClass());
    }

    /**
     * On successful Scrape of an Item the properties of the {@link Item} superclass
     * must be set (not default value)
     */
    @Test
    public void onScrapeValidItemHasTitleFormatGenreAndYear() throws IOException {
        //arrange
        Document document = Jsoup.connect(exampleValidMovieUrl.toString()).timeout(6000).get(); //document point to valid webpage of an item

        //act
        Item result = scraper.scrapeAndGetItem(document);

        //assert
        assertFalse("Title was expected to be set, but is in fact either null, empty or white space.",
                StringUtils.isBlank(result.getTitle()));
        assertFalse("Format was expected to be set, but is in fact either null, empty or white space.",
                StringUtils.isBlank(result.getFormat()));
        assertFalse("Genre was expected to be set, but is in fact either null, empty or white space.",
                StringUtils.isBlank(result.getGenre()));
        assertNotEquals("Year was expected to be set, but is in fact default value (-1).",
                -1, (int) result.getYear());
    }

    /**
     * On successful Scrape of a Book object the properties of {@link Books}
     * must be set (not default value).
     */
    @Test
    public void onScrapeOfBookHasBookPropertiesSet() throws IOException {
        //arrange
        Document document = Jsoup.connect(exampleValidBooksUrl.toString()).timeout(6000).get(); //document point to valid webpage of an item

        //act
        Books result = (Books) scraper.scrapeAndGetItem(document);

        //assert
        assertThat("Book expected to have at least 1 author but instead has 0.",
                result.getAuthors(), hasSize(greaterThan(0)));
        assertFalse("Publisher was expected to be set, but is in fact either null, empty or white space.",
                StringUtils.isBlank(result.getPublisher()));
        assertFalse("ISBN was expected to be set, but is in fact either null, empty or white space.",
                StringUtils.isBlank(result.getISBN()));
    }
}