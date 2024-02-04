package dev.timetable.container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class MongoDBTestContainer {

    static final MongoDBContainer mongoDB;

    static {
        mongoDB = new MongoDBContainer(DockerImageName.parse("mongo:latest"));
        mongoDB.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDB::getReplicaSetUrl);
    }
    
}
