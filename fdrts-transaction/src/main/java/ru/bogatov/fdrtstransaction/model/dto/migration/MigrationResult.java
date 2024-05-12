package ru.bogatov.fdrtstransaction.model.dto.migration;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Builder
@Data
public class MigrationResult {

    Integer entitiesCount;
    OffsetDateTime startTime;
    OffsetDateTime endTime;
    Integer linesProcessed;
    MigrationStatus status;
    String error;
}
