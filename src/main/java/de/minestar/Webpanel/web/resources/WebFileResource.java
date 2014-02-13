package de.minestar.Webpanel.web.resources;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.util.MimeType;

/**
 * Webresource for file delievery lile the css or images
 */
@Path("web")
public class WebFileResource {

    // directory for the web files
    private static final File webFiles = new File("web");

    // The :.+ is for matchin also slashes
    @Path("{file:.+}")
    @GET
    public Response getContent(@PathParam("file") String file) {

        // Load file
        File f = new File(webFiles, file);

        // Throw 404 if not found
        if (!f.exists())
            return Response.status(404).build();

        // Return the file itself
        return Response.ok(f, MimeType.getByFilename(file)).build();
    }
}