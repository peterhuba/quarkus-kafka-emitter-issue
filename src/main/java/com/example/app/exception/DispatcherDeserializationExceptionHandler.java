package com.example.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.Map;

@Slf4j
public class DispatcherDeserializationExceptionHandler implements DeserializationExceptionHandler {

    static int COUNTER;

    @Override
    public DeserializationHandlerResponse handle(ProcessorContext processorContext, ConsumerRecord<byte[], byte[]> consumerRecord, Exception e) {
        if (++COUNTER > 25) {
            log.warn("Too many deserialization exceptions: {}, please verify the setup!", COUNTER);
        }

        return DeserializationHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
