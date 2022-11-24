package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static java.util.Objects.requireNonNull;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/add-invoices")
public class AddInvoice {

    private final Template addInvoice;

    public AddInvoice(Template addInvoice) {
        this.addInvoice = requireNonNull(addInvoice, "page is required");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("token") String token) {

        TemplateInstance invoiceTemplate=addInvoice.data("token", "accessToken");
        return invoiceTemplate;
    }
}
