package rest.service.endpoint;

import org.apache.commons.lang3.StringUtils;
import rest.service.WebCrawler;
import rest.service.WebCrawlerResponse;
import rest.service.model.Books;
import rest.service.model.Item;
import rest.service.model.Movies;
import rest.service.model.Music;

import javax.inject.Singleton;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

@Path("crawler")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class WebCrawlerResource {

    public static final String MSG_ERROR_URL_INVALID = "URL is malformed or invalid.";
    public static final String MSG_ERROR_URL_NULL_EMPTY_WHITESPACE = "URL cannot be NULL, EMPTY or WHITE SPACE.";
    public static final String MSG_ERROR_TYPE_INVALID = "Given Item Type is unsupported. Currently supported types are Books, Movies, Music.";

    /**
     * @param urlAsString - url of website you wish to crawl through
     * @param type        - the class name of the type of object you wish to look for.
     *                    See class names of the implementations of Result abstract class.
     * @param keyword     - the word you wish to look for in the title of the object type
     * @return - on success a collection of results in a JSONObject within a Response object
     * - on error JSONObject of a Response object containing the error message
     */
    @GET
    public Response getContent(@QueryParam("url") String urlAsString,
                               @QueryParam("type") @DefaultValue("") String type,
                               @QueryParam("keyword") @DefaultValue("") String keyword) {
        if (StringUtils.isBlank(urlAsString)) {
            return Response.serverError().entity(MSG_ERROR_URL_NULL_EMPTY_WHITESPACE).build();
        }

        final Item classObjectType = getClassTypeFromStringType(type);
        if (!StringUtils.isBlank(type) && classObjectType == null) {
            return Response.serverError().entity(MSG_ERROR_TYPE_INVALID).build();
        }

        WebCrawler webCrawler;
        Set<Item> result;
        try {
            webCrawler = new WebCrawler(new URL(urlAsString), classObjectType, keyword);
            result = webCrawler.startCrawler();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Response.serverError().entity(MSG_ERROR_URL_INVALID).build();
        }

        WebCrawlerResponse webCrawlerResponse = new WebCrawlerResponse(webCrawler.getStatistic(), result);
        return Response.ok(webCrawlerResponse).build();
    }


    private Item getClassTypeFromStringType(String type) {
        if (type.toLowerCase().equals(Books.class.getSimpleName().toLowerCase())) {
            return new Books();
        } else if (type.toLowerCase().equals(Music.class.getSimpleName().toLowerCase())) {
            return new Music();
        } else if (type.toLowerCase().equals(Movies.class.getSimpleName().toLowerCase())) {
            return new Movies();
        }
        return null;
    }

}
