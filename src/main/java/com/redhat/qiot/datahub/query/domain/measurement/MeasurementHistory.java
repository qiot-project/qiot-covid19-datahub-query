package com.redhat.qiot.datahub.query.domain.measurement;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MeasurementHistory {
    // @BsonId
    // public MeasurementHistoryId id;
    public String date;
    public String country;
    public String city;
    public MeasurementHistoryType specie;
    public int count;
    public double min;
    public double max;
    public double median;
    public double variance;



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((specie == null) ? 0 : specie.hashCode());
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
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (specie != other.specie)
            return false;
        return true;
    }



    @Override
    public String toString() {
        return "MeasurementHistory [date=" + date + ", country=" + country
                + ", city=" + city + ", specie=" + specie + ", count=" + count
                + ", min=" + min + ", max=" + max + ", median=" + median
                + ", variance=" + variance + "]";
    }

}
