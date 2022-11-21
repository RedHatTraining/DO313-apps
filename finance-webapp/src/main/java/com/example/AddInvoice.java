package com.example;

import javax.ws.rs.Path;
import static java.util.Objects.requireNonNull;
import io.quarkus.qute.Template;

@Path("/add-invoices")
public class AddInvoice {

    private final Template addInvoice;

    public AddInvoice(Template addInvoice) {
        this.addInvoice = requireNonNull(addInvoice, "page is required");
    }
}
