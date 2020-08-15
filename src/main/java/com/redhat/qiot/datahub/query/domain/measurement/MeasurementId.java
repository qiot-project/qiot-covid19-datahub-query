package com.redhat.qiot.datahub.query.domain.measurement;

public class MeasurementId {
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public int stationId;
    public String specie;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + hour;
        result = prime * result + minute;
        result = prime * result + month;
        result = prime * result + ((specie == null) ? 0 : specie.hashCode());
        result = prime * result + stationId;
        result = prime * result + year;
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
        MeasurementId other = (MeasurementId) obj;
        if (day != other.day)
            return false;
        if (hour != other.hour)
            return false;
        if (minute != other.minute)
            return false;
        if (month != other.month)
            return false;
        if (specie == null) {
            if (other.specie != null)
                return false;
        } else if (!specie.equals(other.specie))
            return false;
        if (stationId != other.stationId)
            return false;
        if (year != other.year)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MeasurementId [year=" + year + ", month=" + month + ", day="
                + day + ", hour=" + hour + ", minute=" + minute + ", stationId="
                + stationId + ", specie=" + specie + "]";
    }

}
