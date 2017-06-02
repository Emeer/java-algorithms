package com.eztech.deep.learning.kafka.producer;

import com.eztech.deep.learning.kafka.Detection;
import com.eztech.deep.learning.kafka.Detection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Created by jia on 24/05/2017.
 */
public class Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, Detection> kafkaTemplate;

    public void send(String topic, Detection data) {
        LOGGER.info("sending data='{}' to topic='{}'", data, topic);
        kafkaTemplate.send(topic, data);
    }
}
