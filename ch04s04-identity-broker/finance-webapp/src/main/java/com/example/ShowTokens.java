package com.example;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;





import static java.util.Objects.requireNonNull;

@Path("/showtokens")
@Authenticated
public class ShowTokens {
    
    
    private final Template showtokens;
    
    @Inject
    JsonWebToken accessToken;

    public ShowTokens(Template showtokens) {
        
        this.showtokens = requireNonNull(showtokens, "page is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("token") String token) {
       
        TemplateInstance theTemplate=showtokens.data("token", accessToken);
        
        return theTemplate;
    }

}
