package com.redhat.qiot.datahub.query.domain.measurement;

import java.time.Instant;

import javax.json.bind.annotation.JsonbTransient;

import com.redhat.qiot.datahub.query.domain.MeasurementType;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Measurement {
//    public int year;
//    public int month;
//    public int day;
//    public int hour;
//    public int minute;
    @JsonbTransient
    public int stationId;
    @JsonbTransient
    public MeasurementType specie;
    public Instant time;
    public double min;
    public double max;
    public double avg;
    public int count;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((specie == null) ? 0 : specie.hashCode());
        result = prime * result + stationId;
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Measurement other = (Measurement) obj;
        if (specie != other.specie)
            return false;
        if (stationId != other.stationId)
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Measurement [stationId=" + stationId + ", specie=" + specie
                + ", time=" + time + ", min=" + min + ", max=" + max + ", avg="
                + avg + ", count=" + count + "]";
    }

}
