package com.example;

import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.example.model.Campaign;

import io.quarkus.oidc.token.propagation.AccessToken;


@Path("/campaign")
@RegisterRestClient
@AccessToken
public interface CampaignService {
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    Set<Campaign> getAll();
}
