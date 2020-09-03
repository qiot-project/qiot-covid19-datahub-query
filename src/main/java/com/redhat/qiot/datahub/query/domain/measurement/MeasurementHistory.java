package com.redhat.qiot.datahub.query.domain.measurement;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MeasurementHistory {
    @BsonId
    public MeasurementHistoryId id;
    // public Date date;
    // public String country;
    // public String city;
    // public String specie;
    public int count;
    public double min;
    public double max;
    public double median;
    public double variance;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        MeasurementHistory other = (MeasurementHistory) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MeasurementHistory [id=" + id + ", count=" + count + ", min="
                + min + ", max=" + max + ", median=" + median + ", variance="
                + variance + "]";
    }

}
