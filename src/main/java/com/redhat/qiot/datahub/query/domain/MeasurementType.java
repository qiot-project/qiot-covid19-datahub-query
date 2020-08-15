package com.redhat.qiot.datahub.query.domain;

import com.redhat.qiot.datahub.query.domain.measurement.MeasurementHistoryType;

public enum MeasurementType {

    /**
     * 
     */
    pm2_5(MeasurementHistoryType.pm25),
    /**
     * 
     */
    pm10(MeasurementHistoryType.pm10),
    /**
     * 
     */
    oxidising(MeasurementHistoryType.o3),
    /**
     * 
     */
    nh3(null);

    private MeasurementHistoryType historySpecie;

    private MeasurementType(MeasurementHistoryType historySpecie) {
        this.historySpecie = historySpecie;
    }

    public MeasurementHistoryType getHistorySpecie() {
        return historySpecie;
    }
}
