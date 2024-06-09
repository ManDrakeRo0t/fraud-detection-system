package ru.bogatov.fdrtscore.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bogatov.fdrtscore.model.dto.migration.MigrationResult;
import ru.bogatov.fdrtscore.service.MigrationService;


@RestController
@RequestMapping("/api/migration")
public class MigrationController {

    private final MigrationService migrationService;

    public MigrationController(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startMigration() {
        return ResponseEntity.ok(migrationService.start(null));
    }

    @GetMapping("/status")
    public ResponseEntity<MigrationResult> getMigrationResult() {
        return ResponseEntity.ok(migrationService.getStatus());
    }

    @PostMapping(value = "/start-ex")
    public ResponseEntity<String> startMigrationWithFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(migrationService.start(file));
    }

}
