package com.redhat.qiot.datahub.query.domain.station;

import com.mongodb.client.model.geojson.Point;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OtherMeasurementStation {
    public String country;
    public String city;
    public Point location;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
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
        OtherMeasurementStation other = (OtherMeasurementStation) obj;
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
        return true;
    }

    @Override
    public String toString() {
        return "OtherMeasurementStation [country=" + country + ", city=" + city
                + ", location=" + location + "]";
    }


}
