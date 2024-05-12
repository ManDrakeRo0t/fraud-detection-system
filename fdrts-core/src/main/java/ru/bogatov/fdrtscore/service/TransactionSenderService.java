package ru.bogatov.fdrtscore.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.bogatov.fdrtscore.model.dto.TransactionEvent;

import java.util.List;

import static ru.bogatov.fdrtscore.config.rabbit.RabbitConfig.TRANSACTION_EXCHANGE;

@Service
public class TransactionSenderService {

    private final RabbitTemplate rabbitTemplate;

    public TransactionSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendTransactions(List<TransactionEvent> list) {
        list.forEach(this::sendEvent);
    }

    public void sendTransactionEvent(TransactionEvent event) {
        this.sendEvent(event);
    }

    private void sendEvent(TransactionEvent transaction) {
        rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, "transaction.new", transaction);
    }
}
