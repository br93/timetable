package dev.timetable.rabbitmq;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import dev.timetable.container.RabbitMQTestContainer;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class RabbitMQTest extends RabbitMQTestContainer{

    @Autowired
    private Producer producer;

    private Message message;

    @BeforeEach
    void setup(){
        message = new Message("TEST-ID", "TEST-TOKEN", "TEST-AUTHOR");
    }

    @Test
    void shouldProduceAndConsumeMessage(CapturedOutput output) {

        producer.send(message);
        Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> output.toString().contains("TEST"));
    }

    
}
