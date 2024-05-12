package ru.bogatov.fdrtstransaction.controller.ws;

import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import ru.bogatov.fdrtstransaction.model.TransactionEvent;

@Controller
public class TransactionWsController {

    @SubscribeMapping("events.transactions")
    public TransactionEvent fetchEvents() {
        return null;
    }

}
