package com.example;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/showinvoices")
@RolesAllowed("finance-user")
public class ShowInvoices {

    private final Template showInvoices;    
    JSONArray invoices = new JSONArray();
    String jsonFileName = "invoices.json";

    public ShowInvoices(Template showInvoices) {
        this.showInvoices = requireNonNull(showInvoices, "page is required");
    }

    public Object read_invoices() throws FileNotFoundException, IOException, URISyntaxException, ParseException {
        URL fileResource = this.getClass().getClassLoader().getResource( jsonFileName );
        File jsonResourceFile = new File( fileResource.toURI() );
        return new JSONParser().parse( new FileReader( jsonResourceFile) );
    }

    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    public TemplateInstance get() {

        // Get invoice data from Json file
        try{
          invoices = (JSONArray) read_invoices();
        }catch(IOException|URISyntaxException|ParseException excp){
          System.out.println("ERROR: "+excp);
          excp.printStackTrace();
          invoices = new JSONArray();
          
          //invoices.put("error", excp.toString());  
        }        
        TemplateInstance invoiceListTemplate=showInvoices.data("invoices", invoices);
        return invoiceListTemplate;


        
    }

}
