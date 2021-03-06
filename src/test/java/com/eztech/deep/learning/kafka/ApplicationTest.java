package com.eztech.deep.learning.kafka;


import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import com.eztech.deep.learning.kafka.consumer.Receiver;
import com.eztech.deep.learning.kafka.producer.Sender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by jia on 24/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;


    @Before
    public void setUp() throws Exception {
        // wait until the partitions are assigned
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
                .getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer,
                    AllSpringKafkaTests.embeddedKafka.getPartitionsPerTopic());
        }
    }


    @Test
    public void testReceive() throws Exception {
        sender.send(AllSpringKafkaTests.RECEIVER_TOPIC, getDetection());

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        // check that the message was received
        assertEquals(receiver.getLatch().getCount(),0);
    }


    private Detection getDetection() {
        Detection detection = new Detection();
        detection.setType(DetectionType.TEXT);
        detection.setText("Hello Spring Kafka!");
        return detection;
    }
}
