package org.qiot.covid19.datahub.query.rest;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.qiot.covid19.datahub.query.domain.MeasurementDataSet;
import org.qiot.covid19.datahub.query.domain.MeasurementType;
import org.qiot.covid19.datahub.query.service.QueryService;
import org.slf4j.Logger;

@Path("snapshot")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class SnapshotResource {

    @Inject
    Logger LOGGER;

    @Inject
    QueryService queryService;

    @GET
    @Path("/id/{id}")
    public Map<MeasurementType, MeasurementDataSet> getDatasetByStationId(
            @PathParam("id") int stationId,
            @QueryParam("timezoneOffset") Byte timezoneOffset) {
        LOGGER.info(
                "Handling request for station id #{} and timezone offset {}",
                stationId, timezoneOffset);
        return queryService.getSnapshot(stationId);
    }
    //
    // @GET
    // @Path("/serial/{serial}")
    // public Map<MeasurementType, MeasurementDataSet> getDatasetBySerial(
    // @PathParam("serial") String serial) {
    // return queryService.getSnapshot(serial);
    // }
}