package com.redhat.qiot.datahub.query.domain;

import java.util.List;

import com.redhat.qiot.datahub.query.domain.measurement.Measurement;

public class MeasurementDataSet {
    public Measurement last;

    public List<Measurement> lastHourByMinute;
    public List<Measurement> lastDayByHour;
    public List<Measurement> lastMonthByDay;
    public List<Measurement> allMonths;
    public Measurement sixMonthsAgo;
    public Measurement oneYearAgo;

}
