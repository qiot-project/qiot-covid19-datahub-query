package org.qiot.covid19.datahub.query.domain;

import java.util.List;

import org.qiot.covid19.datahub.query.domain.measurement.Measurement;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MeasurementDataSet {
    public Measurement last;

    public List<Measurement> lastHourByMinute;
    public List<Measurement> lastDayByHour;
    public List<Measurement> lastMonthByDay;
    public List<Measurement> allMonths;
    public Measurement sixMonthsAgo;
    public Measurement oneYearAgo;

}
