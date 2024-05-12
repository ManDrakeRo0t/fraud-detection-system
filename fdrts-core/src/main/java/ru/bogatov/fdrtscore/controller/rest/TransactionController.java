package ru.bogatov.fdrtscore.controller.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bogatov.fdrtscore.model.dto.request.TransactionEmulationRequest;
import ru.bogatov.fdrtscore.model.dto.request.TransactionRequest;
import ru.bogatov.fdrtscore.service.TransactionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(transactionService.getCategories());
    }

    @PostMapping
    public ResponseEntity<UUID> sendTransactionEvent(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.sendTransactionEvent(transactionRequest));
    }

    @PostMapping("/emulate")
    public ResponseEntity<Void> emulateTransactionActivity(@RequestBody TransactionEmulationRequest request) {
        return ResponseEntity.ok(transactionService.startEmulation(request));
    }

}
