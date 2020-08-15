/**
 * 
 */

package com.redhat.qiot.datahub.query.service;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.redhat.qiot.datahub.query.domain.station.MeasurementStation;

/**
 * @author abattagl
 *
 */
@Path("/v1/measurementstation")
@RegisterRestClient(configKey = "registration-service-api")
@ApplicationScoped
public interface RegistrationServiceClient {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/id/{id}")
    MeasurementStation getMeasurementStation(@PathParam("id") int id);
}
