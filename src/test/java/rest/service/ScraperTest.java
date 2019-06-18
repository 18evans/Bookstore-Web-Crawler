package rest.service;

import junitparams.JUnitParamsRunner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import rest.service.model.Item;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(JUnitParamsRunner.class)
public class ScraperTest {

    private final Scraper scraper = new Scraper(); //SUT
    /**
     * Since URL is pointing to FHICT self service Web host required is Cisco VPN to connect.
     */
    private static URL exampleValidUrl;

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        exampleValidUrl = new URL("http://i367506.hera.fhict.nl/webcrawl_example/details.php?id=201");
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
        assertNull(result);
    }

    /**
     * If Jsoup {@link Document} object points to a page of a supported item category,
     * returned will be  be null instance of Item.
     */
    @Test
    public void ifPageIsAValidItemPageDoNotReturnNull() throws IOException {
        //arrange - document point to valid webpage of an item
        Document document = Jsoup.connect(exampleValidUrl.toString()).timeout(6000).get();

        //act
        Item result = scraper.scrapeAndGetItem(document);

        //assert
        assertNotNull(result);

    }
}