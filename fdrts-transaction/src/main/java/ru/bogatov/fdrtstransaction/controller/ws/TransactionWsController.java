package ru.bogatov.fdrtstransaction.controller.ws;

import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.bogatov.fdrtstransaction.model.TransactionEvent;

@CrossOrigin
@Controller
public class TransactionWsController {

    @SubscribeMapping("events.transactions")
    public TransactionEvent fetchEvents() {
        return null;
    }

}
