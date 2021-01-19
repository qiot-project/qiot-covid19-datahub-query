package org.qiot.covid19.datahub.query.service;

import java.util.Map;

import org.qiot.covid19.datahub.query.domain.MeasurementDataSet;
import org.qiot.covid19.datahub.query.domain.MeasurementType;

public interface QueryService {

    Map<MeasurementType, MeasurementDataSet> getSnapshot(int stationId);


}
