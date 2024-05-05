package ru.bogatov.fdrtscore.model.dto.migration;

import lombok.Builder;
import lombok.Data;
import org.jooq.impl.QOM;

import java.time.OffsetDateTime;

@Builder
@Data
public class MigrationResult {

    Integer entitiesCount;
    Integer createdCustomers;
    Integer createdMerchants;
    OffsetDateTime startTime;
    OffsetDateTime endTime;
    Integer linesProcessed;
    MigrationStatus status;
    String error;
}
