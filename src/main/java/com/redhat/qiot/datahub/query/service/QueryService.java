package com.redhat.qiot.datahub.query.service;

import java.util.Map;

import com.redhat.qiot.datahub.query.domain.MeasurementDataSet;
import com.redhat.qiot.datahub.query.domain.MeasurementType;

public interface QueryService {

    Map<MeasurementType, MeasurementDataSet> getSnapshot(int stationId);


}
