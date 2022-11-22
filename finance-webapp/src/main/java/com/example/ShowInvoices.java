package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
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

@Path("/show-invoices")
public class ShowInvoices {

    private final Template showInvoices;
    ClassLoader classLoader = getClass().getClassLoader();
    JSONObject invoiceDetailsJson = new JSONObject(), finalInvoice = new JSONObject();
    boolean isInvoicePresent = false;
    String jsonFileName = "invoices.json";

    public ShowInvoices(Template showInvoices) {
        this.showInvoices = requireNonNull(showInvoices, "page is required");
    }

    public Object read_invoices() throws FileNotFoundException, IOException, URISyntaxException, ParseException {
        URL fileResource = classLoader.getResource( jsonFileName );
        File jsonResourceFile = new File( fileResource.toURI() );
        return new JSONParser().parse( new FileReader( jsonResourceFile.toPath().toString() ) );
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getInvoices(@QueryParam("invoice") String invoice) throws FileNotFoundException, IOException, URISyntaxException, ParseException {

        // Get invoice data from Json file
        JSONObject invoiceData = (JSONObject) read_invoices();
        Object obj = invoiceData.get( "invoice" );
        JSONObject invoiceObj = (JSONObject) obj;

        // Get invoice by invoice number
        if( invoiceObj.containsKey(invoice) ) {
            isInvoicePresent = true;
            Object invoiceDetails = invoiceObj.get( invoice );
            finalInvoice = (JSONObject) invoiceDetails;
        }

        // If invoice number cannot be found
        if ( isInvoicePresent == false ) {
            invoiceDetailsJson.put( "Invoice Number", invoice );
            invoiceDetailsJson.put( "message", "Invoice cannot be found!!" );

            return( invoiceDetailsJson.toString() );
        }

        // Build the response
        invoiceDetailsJson.put( "Invoice Number", invoice );
        invoiceDetailsJson.put( "Invoice Details", finalInvoice );

        return( invoiceDetailsJson.toString() );
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("token") String token) {

        TemplateInstance invoiceListTemplate=showInvoices.data("token", "accessToken");
        return invoiceListTemplate;
    }
}
