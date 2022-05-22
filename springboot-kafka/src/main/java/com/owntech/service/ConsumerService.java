package com.owntech.service;

import com.owntech.Constants;
import com.owntech.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @KafkaListener(topics = Constants.KAFKA_TOPIC)
    public void processMessage(@Payload Model content) {
        log.info("Received content from Kafka: {}", content);
        this.webSocket.convertAndSend(Constants.WEBSOCKET_DESTINATION, content.getMessage());
    }
}
