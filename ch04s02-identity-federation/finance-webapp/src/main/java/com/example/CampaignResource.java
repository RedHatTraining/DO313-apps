package com.example;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.example.model.Campaign;



@Path("/showcampaigns")
public class CampaignResource {
    @Inject
    @RestClient
    CampaignService campaignService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/list")
    public String getAll() {
        Set<Campaign> listCampaigns=campaignService.getAll();
        String htmlResponse="<table border='1'><tr><th>Campaig name</th><th>Campaign description</th></tr>";
        for (Campaign campaign: listCampaigns){
            htmlResponse+="<tr><td>"+campaign.name+"</td><td>"+campaign.description+"</td></tr>";
        }
        htmlResponse+="<table><div><a href='/finance'>Back</a></div> ";
       
        return htmlResponse;
    }

}
