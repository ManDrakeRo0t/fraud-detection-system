package ru.bogatov.fdrtscore.model.dto.request;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TransactionRequest {

    private String ccNum;
    private String merchantName;
    private OffsetDateTime dateTime;
    private String category;
    private Float amount;
    private Boolean validate;

}
