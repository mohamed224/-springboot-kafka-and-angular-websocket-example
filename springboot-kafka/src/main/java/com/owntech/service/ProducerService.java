package com.owntech.service;

import com.owntech.Constants;
import com.owntech.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ProducerService {

    private ProducerCallback producerCallback = new ProducerCallback();

    @Autowired
    private KafkaTemplate<String, Model> kafkaTemplate;


    public void sendMessage(Integer amount){
        IntStream.range(0, amount)
                .peek(i -> this.waitFor(1))
                .mapToObj(i -> new Model("Message " + i))
                .forEach(this::sendToKafka);
    }
    private void sendToKafka(Model model) {
        this.kafkaTemplate
                .send(Constants.KAFKA_TOPIC, UUID.randomUUID().toString(), model)
                .addCallback(this.producerCallback);
    }

    private void waitFor(int seconds) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Future<Void> future = scheduler.schedule(() -> null, seconds, TimeUnit.SECONDS);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    class ProducerCallback implements ListenableFutureCallback<SendResult<String, Model>> {
        @Override
        public void onSuccess(SendResult<String, Model> result) {
            RecordMetadata record = result.getRecordMetadata();
            log.info("Sending {} to topic {} - partition {}",
                    result.getProducerRecord().key(),
                    result.getProducerRecord().topic(),
                    record.partition());
        }
        @Override
        public void onFailure(Throwable ex) {
            log.error("Producer Failure", ex);
        }
    }

}
