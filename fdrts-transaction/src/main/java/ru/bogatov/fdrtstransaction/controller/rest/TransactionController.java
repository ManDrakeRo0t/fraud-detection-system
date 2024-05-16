package ru.bogatov.fdrtstransaction.controller.rest;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bogatov.fdrtstransaction.model.database.jooq.tables.pojos.TransactionHistory;
import ru.bogatov.fdrtstransaction.service.TransactionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionHistory> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @GetMapping("/customer/{ccNum}")
    public ResponseEntity<List<TransactionHistory>> getCustomerTransactions(@PathVariable String ccNum) {
        return ResponseEntity.ok(transactionService.getCustomerTransactions(ccNum));
    }

    @GetMapping("/merchant/{name}")
    public ResponseEntity<List<TransactionHistory>> getMerchantTransactions(@PathVariable String name) {
        return ResponseEntity.ok(transactionService.getMerchantTransactions(name));
    }


}
