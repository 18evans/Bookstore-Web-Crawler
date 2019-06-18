package rest.service;

import junitparams.JUnitParamsRunner;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import rest.service.model.Item;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(JUnitParamsRunner.class)
public class ScraperTest {

    private final Scraper scraper = new Scraper(); //SUT

    /**
     * If Jsoup {@link Document} object doesn't point to page of item,
     * returned will be null instance of Item.
     */
    @Test
    public void onScrapeIfParameterPageDoesNotPointToItemReturnNull() {
        //arrange
        Document document = mock(Document.class);
        when(document.body()).thenReturn(null); //document points to null page

        //act
        Item result = scraper.scrapeAndGetItem(document);

        //assert
        assertNull(result);
    }

}