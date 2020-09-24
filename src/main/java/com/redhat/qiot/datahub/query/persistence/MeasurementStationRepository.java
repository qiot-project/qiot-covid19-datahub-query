//package com.redhat.qiot.datahub.query.persistence;
//
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.event.Observes;
//import javax.inject.Inject;
//
//import org.bson.codecs.configuration.CodecRegistry;
//import org.eclipse.microprofile.config.inject.ConfigProperty;
//import org.slf4j.Logger;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.redhat.qiot.datahub.query.domain.station.MeasurementStation;
//import com.redhat.qiot.datahub.query.util.producers.StationCodecRegistryQualifier;
//
//import io.quarkus.runtime.StartupEvent;
//
//@ApplicationScoped
//public class MeasurementStationRepository {
//
//    @ConfigProperty(name = "qiot.database.name")
//    String DATABASE_NAME;
//
//    @ConfigProperty(name = "qiot.measurement.grain.day.collection-name")
//    String COLLECTION_NAME;
//
//    @Inject
//    Logger LOGGER;
//
//    @Inject
//    MongoClient mongoClient;
//
//    MongoDatabase qiotDatabase = null;
//    MongoCollection<MeasurementStation> collection = null;
//    
//    @Inject
//    @StationCodecRegistryQualifier
//    CodecRegistry pojoCodecRegistry;
//
//    void onStart(@Observes StartupEvent ev) {
//    }
//
//    @PostConstruct
//    void init() {
//        qiotDatabase = mongoClient.getDatabase(DATABASE_NAME);
//        collection = qiotDatabase.getCollection(COLLECTION_NAME, MeasurementStation.class);
//
//        collection = collection.withCodecRegistry(pojoCodecRegistry);
//    }
//
//}
