package com.example;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;

@Provider//register as JAXRS provider
@Priority(1)//override the build-int mapper(which has 5001 prio)
public class GlobalExceptionMapper implements ExceptionMapper<Exception>{
  @Override
  public Response toResponse(Exception exception) {   
    return mapExceptionToResponse(exception);    
  }
  private Response mapExceptionToResponse(Exception exception) {
    exception.printStackTrace();
    // Use response from WebApplicationException as they are
    if (exception instanceof WebApplicationException) {
      
      // Overwrite error message
      Response originalErrorResponse = ((WebApplicationException) exception).getResponse();
      return Response.fromResponse(originalErrorResponse)
                     .entity(originalErrorResponse.getStatusInfo().getReasonPhrase())
                     .build();
    }
    // Special mappings
    else if (exception instanceof UnauthorizedException) {      
      return Response.status(401).entity(exception.getMessage()).build();
    }
   
    else if (exception instanceof IllegalArgumentException) {      
      return Response.status(400).entity("You need to be authenticated").build();
    }
    // Use 500 (Internal Server Error) for all other
    else {
      return Response.serverError().entity("Internal Server Error").build();
    }
  }
}
