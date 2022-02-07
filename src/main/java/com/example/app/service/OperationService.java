package com.example.app.service;

import com.example.app.entity.OperationEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OperationService {

    @Inject
    OperationContextEventsService operationContextEventsService;

    public void createNewOperation(String name) {
    }

    private void saveNewPaymentContext(String name) {
    }

    private void mapNewPaymentContextToDto(OperationEntity operationEntity) {
    }

    private void emitPaymentContextCreatedEvent(OperationEntity operationEntity) {
        operationContextEventsService.emitNewPaymentSequenceContextEvent(operationEntity);
    }

    public void emitPaymentUnitStatusEvent(int paymentContextId) {
        operationContextEventsService.emitNewPaymentUnitStatusEvent(paymentContextId);
    }
}
