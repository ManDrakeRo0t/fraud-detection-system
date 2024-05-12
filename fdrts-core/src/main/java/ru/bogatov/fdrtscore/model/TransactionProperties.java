package ru.bogatov.fdrtscore.model;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class TransactionProperties {

    List<String> categories;
    Float minAmount;
    Float maxAmount;
    OffsetDateTime from;
    Integer duration;

}
