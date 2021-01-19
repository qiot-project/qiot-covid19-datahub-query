package org.qiot.covid19.datahub.query.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.qiot.covid19.datahub.query.domain.MeasurementDataSet;
import org.qiot.covid19.datahub.query.domain.MeasurementType;
import org.qiot.covid19.datahub.query.domain.measurement.Measurement;
import org.qiot.covid19.datahub.query.domain.measurement.MeasurementHistory;
import org.qiot.covid19.datahub.query.domain.measurement.MeasurementHistoryType;
import org.qiot.covid19.datahub.query.domain.station.MeasurementStation;
import org.qiot.covid19.datahub.query.domain.station.OtherMeasurementStation;
import org.qiot.covid19.datahub.query.persistence.QIoTRepository;
import org.slf4j.Logger;

@ApplicationScoped
public class QueryServiceImpl implements QueryService {
    /**
     * Logger for this class
     */
    @Inject
    Logger LOGGER;

    @Inject
    @RestClient
    RegistrationServiceClient registrationServiceClient;

    @Inject
    QIoTRepository qIoTRepository;

    @Override
    public Map<MeasurementType, MeasurementDataSet> getSnapshot(int stationId) {
        Map<MeasurementType, MeasurementDataSet> dataSets = new HashMap<>();
        for (MeasurementType specie : MeasurementType.values())
            dataSets.put(specie, getDataset(stationId, specie));
        return dataSets;
    }

    private MeasurementDataSet getDataset(int stationId,
            MeasurementType specie) {
        MeasurementDataSet dataSet = new MeasurementDataSet();
        MeasurementHistoryType historySpecie = specie.getHistorySpecie();
        // measurement station
        MeasurementStation ms = qIoTRepository
                .queryMeasurementStation(stationId);
        // third party measurement station
        OtherMeasurementStation oms = qIoTRepository
                .getClosestStation(ms.location);
        // third party historical data
        if (historySpecie != null) {
            dataSet.sixMonthsAgo = getSixMonthsHistory(stationId, specie, oms);
            dataSet.oneYearAgo = getOneYearHistory(stationId, specie, oms);
        }
        // own historical date
        dataSet.last = qIoTRepository.getLastMeasurement(stationId, specie);
        dataSet.lastHourByMinute = qIoTRepository.getLastHourByMinute(stationId,
                specie);
        dataSet.lastDayByHour = qIoTRepository.getLastDayByHour(stationId,
                specie);
        dataSet.lastMonthByDay = qIoTRepository.getLastMonthByDay(stationId,
                specie);
        dataSet.allMonths = qIoTRepository.getAllMonths(stationId, specie);
        return dataSet;
    }

    private Measurement getSixMonthsHistory(int stationId,
            MeasurementType specie, OtherMeasurementStation oms) {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC).minus(6,
                ChronoUnit.MONTHS);
        MeasurementHistory h = qIoTRepository.getSixMonthsAgo(oms.country,
                oms.city, specie.getHistorySpecie());
        
        if(h==null)
            return null;

        return historyToMeasurement(stationId, specie, utc, h);
    }

    private Measurement getOneYearHistory(int stationId, MeasurementType specie,
            OtherMeasurementStation oms) {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC).minus(1,
                ChronoUnit.YEARS);
        MeasurementHistory h = qIoTRepository.getOneYearAgo(oms.country,
                oms.city, specie.getHistorySpecie());
        
        if(h==null)
            return null;

        return historyToMeasurement(stationId, specie, utc, h);

    }

    private Measurement historyToMeasurement(int stationId,
            MeasurementType specie, OffsetDateTime utc, MeasurementHistory h) {
        Measurement m = new Measurement();

        m.stationId = stationId;
        m.time = utc.toInstant();
        m.specie = specie;

        m.avg = h.median;
        m.min = h.min;
        m.max = h.max;
        m.count = h.count;

        return m;
    }

}
