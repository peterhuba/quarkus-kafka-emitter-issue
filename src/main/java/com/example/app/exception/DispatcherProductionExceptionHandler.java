package com.example.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;

import java.util.Map;

@Slf4j
public class DispatcherProductionExceptionHandler implements ProductionExceptionHandler {

    static int COUNTER;

    @Override
    public ProductionExceptionHandlerResponse handle(ProducerRecord<byte[], byte[]> producerRecord, Exception e) {
        if (++COUNTER > 25) {
            log.warn("Too many production exceptions: {}, please verify the setup!", COUNTER);
        }

        return ProductionExceptionHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
