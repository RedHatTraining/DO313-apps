package com.example;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;
import static java.util.Objects.requireNonNull;

@Path("/showcampaigns")
public class ShowCampaigns {
    private final Template showCampaigns;
    public ShowCampaigns(Template showCampaigns) {
        
        this.showCampaigns = requireNonNull(showCampaigns, "page is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("token") String token) {
        
        TemplateInstance theTemplate=showCampaigns.data("token", "accessToken");                    
        return theTemplate;
    }

}