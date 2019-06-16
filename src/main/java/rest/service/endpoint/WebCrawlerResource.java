package rest.service.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import javax.inject.Singleton;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("crawler")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class WebCrawlerResource {

    public static final String MSG_ERROR_URL_INVALID = "URL is malformed or invalid.";
    public static final String MSG_ERROR_URL_NULL_EMPTY_WHITESPACE = "URL cannot be NULL, EMPTY or WHITE SPACE.";

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
        if (!UrlValidator.getInstance().isValid(urlAsString)) {
            return Response.serverError().entity(MSG_ERROR_URL_INVALID).build();
        }
        return Response.ok().build();
    }

}
