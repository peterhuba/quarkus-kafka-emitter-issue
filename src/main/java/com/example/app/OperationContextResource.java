package com.example.app;

import com.example.app.service.OperationService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/paymentContext")
public class OperationContextResource {

    @Inject
    OperationService operationService;

    @POST
    @Path("/createOperation")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createOperation(@Valid @NotNull String name) {
        operationService.createNewOperation(name);

        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/simulateOperationUnitStatus")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response simulateOperationStatusEvent(@QueryParam("operationId") int paymentContextId) {
        operationService.emitPaymentUnitStatusEvent(paymentContextId);

        return Response.status(Response.Status.CREATED).build();
    }
}