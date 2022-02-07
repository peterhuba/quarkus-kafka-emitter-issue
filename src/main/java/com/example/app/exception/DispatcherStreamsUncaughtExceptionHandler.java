package com.example.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class DispatcherStreamsUncaughtExceptionHandler implements StreamsUncaughtExceptionHandler {

    @Override
    public StreamThreadExceptionResponse handle(Throwable throwable) {
        log.warn("Unexpected streams exception, replacing thread", throwable);
        return StreamThreadExceptionResponse.REPLACE_THREAD;
    }
}
