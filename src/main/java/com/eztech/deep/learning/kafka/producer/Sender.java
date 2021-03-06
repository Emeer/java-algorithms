package com.eztech.deep.learning.kafka.producer;

import com.eztech.deep.learning.kafka.Detection;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Created by jia on 24/05/2017.
 */
@Slf4j
public class Sender {


    @Autowired
    private KafkaTemplate<String, Detection> kafkaTemplate;


    public void send(String topic, Detection message) {
        // the KafkaTemplate provides asynchronous send methods returning a Future
        ListenableFuture<SendResult<String, Detection>> future = kafkaTemplate.send(topic, message);

        // register a callback with the listener to receive the result of the send asynchronously
        future.addCallback(new ListenableFutureCallback<SendResult<String, Detection>>() {

            @Override
            public void onSuccess(SendResult<String, Detection> result) {
                log.info("sent message='{}' with offset={}", message,
                        result.getRecordMetadata().offset());
            }


            @Override
            public void onFailure(Throwable ex) {
                log.error("unable to send message='{}'", message, ex);
            }
        });

    }
}
