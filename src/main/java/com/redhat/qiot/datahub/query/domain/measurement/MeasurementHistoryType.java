package com.redhat.qiot.datahub.query.domain.measurement;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum MeasurementHistoryType {
    so2, temperature, humidity, dew, co, o3, pm25, no2, pressure, pm10
}
