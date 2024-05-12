package ru.bogatov.fdrtstransaction.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bogatov.fdrtstransaction.model.dto.migration.MigrationResult;
import ru.bogatov.fdrtstransaction.service.MigrationService;


@RestController
@RequestMapping("/api/migration")
public class MigrationController {

    private final MigrationService migrationService;

    public MigrationController(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startMigration() {
        return ResponseEntity.ok(migrationService.start());
    }

    @GetMapping("/status")
    public ResponseEntity<MigrationResult> getMigrationResult() {
        return ResponseEntity.ok(migrationService.getStatus());
    }

}
