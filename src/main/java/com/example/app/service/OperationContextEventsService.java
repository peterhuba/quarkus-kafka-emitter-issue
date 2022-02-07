package com.example.app.service;

import com.example.app.entity.OperationEntity;
import com.example.app.repository.OperationRepository;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class OperationContextEventsService {

    @Inject
    OperationRepository operationRepository;

    @Inject
    @Channel("operation-context")
    Emitter<Record<String, String>> operationContextEmitter;

    @Inject
    @Channel("operation-unit-status")
    Emitter<Record<String, String>> operationUnitStatusEmitter;

    @ConfigProperty(name = "quarkus.kafka-streams.schema-registry-url")
    String schemaRegistryUrl;

    @ConfigProperty(name = "quarkus.kafka-streams.schema-registry-key")
    String schemaRegistryKey;

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();
        final Map<String, String> serdeConfig = Collections.singletonMap(schemaRegistryKey, schemaRegistryUrl);

        KStream<String, String> paymentUnitStatuses = builder.stream("topic.operation-unit-status",
                Consumed.with(Serdes.String(), Serdes.String()));

        paymentUnitStatuses
                .peek(this::updatePaymentContext)
                .mapValues((paymentContextId, paymentUnitStatus) -> "something")
                .to("topic.operation-status", Produced.with(Serdes.String(), Serdes.String()));

        return builder.build();
    }

    public void emitNewPaymentSequenceContextEvent(OperationEntity operationEntity) {
        String paymentContextId = Long.toString(operationEntity.getId());

        operationContextEmitter.send(Record.of(paymentContextId, "qwert"));
    }

    public void emitNewPaymentUnitStatusEvent(int paymentContextId) {
        log.info("Emitting payment unit status update event: {}", paymentContextId);

        operationUnitStatusEmitter.send(Record.of(Integer.toString(paymentContextId), "asdf"));
    }

    @Transactional
    public void updatePaymentContext(String paymentContextId, String name) {
    }
}
