package rest.service;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import rest.service.endpoint.WebCrawlerResource;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Publisher {
    private static final int PORT = 9090;
    private static final String URL = "http://localhost:" + PORT + "/";

    public static void main(String[] args) {
        final URI baseUri = UriBuilder.fromUri(URL).build();
        ResourceConfig resourceConfig = new ResourceConfig(WebCrawlerResource.class);
        JdkHttpServerFactory.createHttpServer(baseUri, resourceConfig, true);
        System.out.println("Service hosted at " + baseUri);

    }
}