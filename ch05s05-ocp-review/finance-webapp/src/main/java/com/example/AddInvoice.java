package com.example;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/addinvoices")
@RolesAllowed("finance-admin")
public class AddInvoice {

    
    private final Template addInvoice;
  

    String jsonFileName = "invoices.json";
    public AddInvoice(Template addInvoice) {
        this.addInvoice = requireNonNull(addInvoice, "page is required");
    }
    private void addInvoice(String id, String name, String item, String date, String amount) throws FileNotFoundException, IOException, URISyntaxException, ParseException{
        URL fileResource = this.getClass().getClassLoader().getResource( jsonFileName );
        File jsonResourceFile = new File( fileResource.toURI() );
        JSONArray invoicesList=(JSONArray) new JSONParser().parse( new FileReader( jsonResourceFile.toPath().toString() ) );
        JSONObject newInvoice=new JSONObject();
        newInvoice.put("id", id);
        newInvoice.put("name", name);
        newInvoice.put("item", item);
        newInvoice.put("date", date);
        newInvoice.put("amount", amount);
        invoicesList.add(newInvoice);
        
        FileWriter fw= new FileWriter(jsonResourceFile);
        fw.write(invoicesList.toJSONString());
        fw.close();
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public TemplateInstance get(@QueryParam("invoiceId") String id, @QueryParam("name") String name,
            @QueryParam("item") String item, @QueryParam("date") String date, @QueryParam("amount") String amount) {
        
        TemplateInstance invoiceTemplate = null;
        try{
            addInvoice(id,name,item,date,amount);
        }catch(Exception e){
            System.out.println("ERROR: " +e);
            e.printStackTrace();
            invoiceTemplate = addInvoice.data("message", "ERROR adding the invoice");
        }
        invoiceTemplate = addInvoice.data("message", "Invoice "+id+" added.");
        return invoiceTemplate;
    }



}
