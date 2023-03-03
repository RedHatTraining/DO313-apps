package com.example;


import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.quarkus.security.ForbiddenException;

@Provider//register as JAXRS provider
@Priority(1)//override the build-int mapper(which has 5001 prio)
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
  @Override
  public Response toResponse(ForbiddenException exception) {   
    return Response.serverError().entity("{\"message\":\"You are not in the required role\"}").build();    
  }
}
