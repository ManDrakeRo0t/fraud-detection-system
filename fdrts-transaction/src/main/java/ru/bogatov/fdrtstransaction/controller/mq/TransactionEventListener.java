package ru.bogatov.fdrtstransaction.controller.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bogatov.fdrtstransaction.service.TransactionService;

import static ru.bogatov.fdrtstransaction.config.RabbitConfig.TRANSACTION_QUEUE;

@Component
@Slf4j
public class TransactionEventListener {

    @Autowired
    TransactionService transactionService;


    @RabbitListener(queues = TRANSACTION_QUEUE)
    public void receiveMessage(String message) {
        transactionService.handleTransactionEvent(message);
    }
}
