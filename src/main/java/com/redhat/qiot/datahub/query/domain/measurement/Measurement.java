package com.redhat.qiot.datahub.query.domain.measurement;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Measurement {
    @BsonId
    public MeasurementId id;
    public double min;
    public double max;
    public double avg;
    public int count;

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
        Measurement other = (Measurement) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Measurement [id=" + id + ", min=" + min + ", max=" + max
                + ", avg=" + avg + ", count=" + count + "]";
    }

}
