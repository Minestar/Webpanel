package de.minestar.Webpanel.web.resources;

import java.io.File;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.glassfish.grizzly.http.util.MimeType;

import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.web.security.LoginCookie;
import de.minestar.Webpanel.web.security.NewAuthHandler;

/**
 * This resource is for handeling the first page (normally the index.html)
 */
@Path("")
public class MainPageResource {

    // directory for the web files
    private static final File webFolder = new File("web");

    // The :.+ is for matchin also slashes
    @Path("{file:.+}")
    @GET
    public Response getContent(@Context UriInfo uriInfo, @PathParam("file") String file) {
        // Load file
        File f = new File(webFolder, file);

        // Throw 404 if not found
        if (!f.exists()) {
            // retrieve the relative folder
            int countSlashes = StringUtils.countMatches(uriInfo.getPath(), "/");
            String relativeFolder = "";
            for (int count = 1; count < countSlashes; count++) {
                relativeFolder += "../";
            }

            // build response
            return Response.status(Status.NOT_FOUND).entity(Template.get("error404").setRelativeFolder(relativeFolder).build().replaceAll("web/", "")).build();
        }

        // Return the file itself
        return Response.ok(f, MimeType.getByFilename(file)).build();
    }

    @GET
    @Path("{a:index.html|login.html|}")
    @Produces("text/html")
    public Response index(@Context UriInfo uriInfo, @CookieParam(LoginCookie.COOKIE_NAME) LoginCookie cookie) {
        // Has the user a cookie ? Show him the normal menu
        try {
            UserData userData = NewAuthHandler.check(uriInfo, cookie);
            Template template = Template.get("doLogin").set("USERNAME", userData.getUserName()).set("TOKEN", userData.getToken()).setUser(userData);
            return Response.ok(template.build()).build();
        } catch (Exception e) {
        }

        // Deliver the login page if not logged in or wrong cookie
        return Response.ok(Template.get("login").build()).build();
    }

}
