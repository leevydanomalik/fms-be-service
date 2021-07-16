package com.bitozen.fms.service.command.svc.configuration;

import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfiguration {

	@Value("${spring.data.mongodb.host}")
    private String MONGO_HOST;
    @Value("${spring.data.mongodb.port}")
    private String MONGO_PORT;
    @Value("${spring.data.mongodb.database}")
    private String MONGO_DB;

    @Bean
    public MongoClient mongoClient(){
        MongoClient mongoClient = new MongoClient(MONGO_HOST, Integer.valueOf(MONGO_PORT));
        return mongoClient;
    }

    @Bean
    public EventStorageEngine storageEngine(MongoClient client) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client, MONGO_DB).build())
                .eventSerializer(JacksonSerializer.defaultSerializer())
                .build();
    }

    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }
    
    @Bean
    public TokenStore tokenStore(Serializer serializer) {
        return MongoTokenStore.builder()
                .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(mongoClient(),MONGO_DB).build())
                .serializer(serializer)
                .build();
    }
}
