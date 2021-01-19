package org.qiot.covid19.datahub.query.persistence;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.qiot.covid19.datahub.query.domain.MeasurementType;
import org.qiot.covid19.datahub.query.domain.measurement.Measurement;
import org.qiot.covid19.datahub.query.domain.measurement.MeasurementHistory;
import org.qiot.covid19.datahub.query.domain.measurement.MeasurementHistoryType;
import org.qiot.covid19.datahub.query.domain.station.MeasurementStation;
import org.qiot.covid19.datahub.query.domain.station.OtherMeasurementStation;
import org.slf4j.Logger;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.geojson.Point;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class QIoTRepository {

    @ConfigProperty(name = "qiot.database.name")
    String DATABASE_NAME;

    // @ConfigProperty(name = "qiot.measurement.grain.coarse.gas.name")
    // String COLLECTION_NAME;

    @Inject
    Logger LOGGER;

    @Inject
    MongoClient mongoClient;

    CodecProvider codecProvider;
    CodecRegistry codecRegistry;

    MongoDatabase database;

    MongoCollection<OtherMeasurementStation> omsCollection;
    MongoCollection<MeasurementStation> msCollection;
    MongoCollection<MeasurementHistory> mhCollection;
    MongoCollection<Measurement> mgmCollection;
    MongoCollection<Measurement> hgmCollection;
    MongoCollection<Measurement> dgmCollection;
    MongoCollection<Measurement> zgmCollection;

    void onStart(@Observes StartupEvent ev) {
    }

    // qiot.measurement.grain.minute.collection-name=measurementbyminute
    // qiot.measurement.grain.hour.collection-name=measurementbyhour
    // qiot.measurement.grain.day.collection-name=measurementbyday
    // qiot.measurement.grain.month.collection-name=measurementbymonth

    @PostConstruct
    void init() {
        database = mongoClient.getDatabase(DATABASE_NAME);

        // Create a CodecRegistry containing the PojoCodecProvider instance.
        codecProvider = PojoCodecProvider.builder()
                .register("com.mongodb.client.model",
                        "org.qiot.covid19.datahub.query.domain",
                        "org.qiot.covid19.datahub.query.domain.measurement",
                        "org.qiot.covid19.datahub.query.domain.station")
                .automatic(true).build();
        codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(codecProvider));

        omsCollection = database
                .getCollection("othermeasurementstation",
                        OtherMeasurementStation.class)
                .withCodecRegistry(codecRegistry);
        msCollection = database
                .getCollection("measurementstation", MeasurementStation.class)
                .withCodecRegistry(codecRegistry);
        mhCollection = database.getCollection("mh", MeasurementHistory.class)
                .withCodecRegistry(codecRegistry);
        mgmCollection = database.getCollection("a_minute", Measurement.class)
                .withCodecRegistry(codecRegistry);
        hgmCollection = database.getCollection("a_hour", Measurement.class)
                .withCodecRegistry(codecRegistry);
        dgmCollection = database.getCollection("a_day", Measurement.class)
                .withCodecRegistry(codecRegistry);
        zgmCollection = database.getCollection("a_month", Measurement.class)
                .withCodecRegistry(codecRegistry);
    }

    // private void getUTCDate() {
    // OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC)
    // .truncatedTo(ChronoUnit.MINUTES);
    // Instant min = utc.minus(2L, ChronoUnit.MINUTES).toInstant();
    // LOGGER.debug("Date MIN = {}", min);
    // Instant max = utc.minus(1L, ChronoUnit.MINUTES).toInstant();
    // LOGGER.debug("Date MAX = {}", max);
    // }

    public MeasurementStation queryMeasurementStation(int id) {
        LOGGER.debug("Searching for Measurement Station with ID {}", id);
        MeasurementStation ms = msCollection.find(Filters.eq("_id", id))
                .first();
        if (ms == null)
            LOGGER.debug("\n\nNo Measurement Station with ID {}\n", id);
        else
            LOGGER.debug("\n\nFound MeasurementStation {}\n", ms);
        return ms;
    }

    public OtherMeasurementStation getClosestStation(Point refPoint) {
        // FindIterable<OtherMeasurementStation> omsFilter = omsCollection
        // .find(Filters.near("location", refPoint, null, null));
        // for (OtherMeasurementStation oms : omsFilter)
        // logger.debug("The closest Measurement Station is {}", oms);
        return omsCollection
                .find(Filters.near("location", refPoint, null, null)).limit(1)
                .first();
    }

    public MeasurementHistory getSixMonthsAgo(String country, String city,
            MeasurementHistoryType specie) {
        String date = OffsetDateTime.now(ZoneOffset.UTC)
                .minus(6, ChronoUnit.MONTHS)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
        MeasurementHistory mh = mhCollection.find(//
                Filters.and(//
                        Filters.eq("date", date), //
                        Filters.eq("country", country), //
                        Filters.eq("city", city), //
                        Filters.eq("specie", specie.toString())//
                )//
        ).sort(Sorts.descending("time")).limit(1).first();
        if (mh == null)
            LOGGER.debug(
                    "\n\nNo MeasurementHistory found for date={} and country={} and city={} and specie={}\n",
                    date, country, city, specie);
        else
            LOGGER.debug("\n\nFound MeasurementHistory {}\n", mh);
        return mh;
    }

    public MeasurementHistory getOneYearAgo(String country, String city,
            MeasurementHistoryType specie) {
        String date = OffsetDateTime.now(ZoneOffset.UTC)
                .minus(1, ChronoUnit.YEARS)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
        MeasurementHistory mh = mhCollection.find(//
                Filters.and(//
                        Filters.eq("date", date), //
                        Filters.eq("country", country), //
                        Filters.eq("city", city), //
                        Filters.eq("specie", specie.toString())//
                )//
        ).limit(1).first();
        if (mh == null)
            LOGGER.debug(
                    "\n\nNo MeasurementHistory found for date={} and country={} and city={} and specie={}\n",
                    date, country, city, specie);
        else
            LOGGER.debug("\n\nFound MeasurementHistory {}\n", mh);
        return mh;
    }

    public Measurement getLastMeasurement(int stationId,
            MeasurementType specie) {
        Measurement last = mgmCollection
                .find(Filters.and(Filters.eq("stationId", stationId),
                        Filters.eq("specie", specie.toString())))
                .sort(Sorts.descending("time")).limit(1).first();
        if (last == null)
            LOGGER.debug(
                    "\n\nNo Measurement found for stationId={} and specie={}\n",
                    stationId, specie);
        else
            LOGGER.debug("\n\nFound last Measurement {}\n", last);
        return last;
    }

    public List<Measurement> getLastHourByMinute(int stationId,
            MeasurementType specie) {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC).minus(1,
                ChronoUnit.HOURS);
        FindIterable<Measurement> dataIterable = mgmCollection.find(//
                Filters.and(//
                        Filters.gte("time", utc.toInstant()), //
                        Filters.eq("stationId", stationId), //
                        Filters.eq("specie", specie.toString() //

                        )));
        List<Measurement> values = new ArrayList<>();
        for (Measurement m : dataIterable)
            values.add(m);
        LOGGER.debug("\n\nLastHourByMinute {}",
                Arrays.toString(values.toArray()));
        return values;
    }

    public List<Measurement> getLastDayByHour(int stationId,
            MeasurementType specie) {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC).minus(1,
                ChronoUnit.DAYS);
        FindIterable<Measurement> dataIterable = hgmCollection.find(//
                Filters.and(//
                        Filters.gte("time", utc.toInstant()), //
                        Filters.eq("stationId", stationId), //
                        Filters.eq("specie", specie.toString() //
                        )));
        List<Measurement> values = new ArrayList<>();
        for (Measurement m : dataIterable)
            values.add(m);
        LOGGER.debug("\n\nLastDayByHour {}", Arrays.toString(values.toArray()));
        return values;
    }

    public List<Measurement> getLastMonthByDay(int stationId,
            MeasurementType specie) {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC).minus(1,
                ChronoUnit.MONTHS);
        FindIterable<Measurement> dataIterable = dgmCollection.find(//
                Filters.and(//
                        Filters.gte("time", utc.toInstant()), //
                        Filters.eq("stationId", stationId), //
                        Filters.eq("specie", specie.toString() //
                        )));
        List<Measurement> values = new ArrayList<>();
        for (Measurement m : dataIterable)
            values.add(m);
        LOGGER.debug("\n\nLastMonthByDay {}", Arrays.toString(values.toArray()));
        return values;
    }

    public List<Measurement> getAllMonths(int stationId,
            MeasurementType specie) {
        FindIterable<Measurement> dataIterable = zgmCollection.find(//
                Filters.and(//
                        Filters.eq("stationId", stationId), //
                        Filters.eq("specie", specie.toString())//
                ));
        List<Measurement> values = new ArrayList<>();
        for (Measurement m : dataIterable)
            values.add(m);
        LOGGER.debug("\n\nAllMonths {}", Arrays.toString(values.toArray()));
        return values;
    }
}
