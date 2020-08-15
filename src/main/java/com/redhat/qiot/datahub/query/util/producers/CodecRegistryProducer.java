package com.redhat.qiot.datahub.query.util.producers;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.redhat.qiot.datahub.query.domain.measurement.Measurement;
import com.redhat.qiot.datahub.query.domain.station.MeasurementStation;

@ApplicationScoped
public class CodecRegistryProducer {

    private PojoCodecProvider measurementCodecProvider;
    private CodecRegistry measurementCodecRegistry;
    private PojoCodecProvider stationCodecProvider;
    private CodecRegistry stationCodecRegistry;

    @PostConstruct
    void init() {
        // Create a CodecRegistry containing the PojoCodecProvider instance.
        measurementCodecProvider = PojoCodecProvider.builder()
                .register(Measurement.class.getPackageName()).automatic(true)
                .build();
        measurementCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(measurementCodecProvider));
        // Create a CodecRegistry containing the PojoCodecProvider instance.
        stationCodecProvider = PojoCodecProvider.builder()
                .register(MeasurementStation.class.getPackageName())
                .automatic(true).build();
        stationCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(stationCodecProvider));
    }

    @Produces
    @MeasurementCodecRegistryQualifier
    public CodecRegistry getMeasurementCodecRegistry() {
        return measurementCodecRegistry;
    }

    @Produces
    @StationCodecRegistryQualifier
    public CodecRegistry getStationCodecRegistry() {
        return stationCodecRegistry;
    }

}
