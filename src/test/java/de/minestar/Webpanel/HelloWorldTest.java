package de.minestar.Webpanel;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import de.minestar.Webpanel.core.WebpanelSettings;
import de.minestar.Webpanel.web.WebPanelApplication;

/**
 * Simple hello world test - useful to check if the testing is working and the
 * server is starting
 * 
 */
public class HelloWorldTest extends JerseyTest {

    @Override
    protected Application configure() {
        WebpanelSettings settings = new WebpanelSettings(".");
        return new WebPanelApplication(settings.getPackageFolder());
    }

    @Test
    public void testRequest() {
        // Call the rest resource test/helloWorld - it is a get method
        String response = target("test").path("helloWorld").request().get(String.class);
        assertEquals(response, "Hello World");

        // Call the rest resource test/helloName - it is a get method
        response = target("test").path("hello").path("GeMo").request().get(String.class);
        assertEquals(response, "Hello GeMo");
    }
}
