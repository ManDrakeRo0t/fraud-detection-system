package ru.bogatov.fdrtscore.model.dto.request;

import lombok.Data;

@Data
public class TransactionEmulationRequest {

    Integer fraudPercent;
    Integer transactionsAmount;
    Integer delay;
}
