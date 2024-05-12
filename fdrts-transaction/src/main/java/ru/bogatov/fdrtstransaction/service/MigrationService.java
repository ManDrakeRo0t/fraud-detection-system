package ru.bogatov.fdrtstransaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import ru.bogatov.fdrtstransaction.model.database.jooq.tables.pojos.TransactionHistory;
import ru.bogatov.fdrtstransaction.model.dto.migration.MigrationResult;
import ru.bogatov.fdrtstransaction.model.dto.migration.MigrationStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class MigrationService {

    private final TransactionService transactionService;

    private final MigrationResult state;

    private File migrationData;

    private int batchSize = 200;


    public MigrationService(TransactionService transactionService) {
        this.transactionService = transactionService;

        state = MigrationResult.builder()
                .status(MigrationStatus.NOT_STARTED)
                .entitiesCount(0)
                .linesProcessed(0)
                .build();
    }

    public MigrationResult getStatus() {
        return state;
    }


    public String start() {
        if (!isStartPossible()) {
            return "Start not possible, migration in progress";
        }

        state.setStatus(MigrationStatus.IN_PROGRESS);
        state.setStartTime(OffsetDateTime.now());
        state.setLinesProcessed(0);
        state.setEndTime(null);
        state.setEntitiesCount(0);

        transactionService.trancuate(true);

        Executors.newSingleThreadExecutor().execute(() -> {

            try {
                Scanner scanner = loadFile();


                List<String> values = readBatch(scanner);

                while (!values.isEmpty()) {

                    List<TransactionHistory> transactions = new ArrayList<>();


                    state.setLinesProcessed(state.getLinesProcessed() + values.size());
                    values.forEach(line -> {
                        TransactionHistory data = readFromLine(line);

                        transactions.add(data);
                        state.setEntitiesCount(state.getEntitiesCount() + 1);

                    });

                    transactionService.batchInsert(transactions);




                    values = readBatch(scanner);
                }
                state.setStatus(MigrationStatus.COMPLETED);
                state.setEndTime(OffsetDateTime.now());

            } catch (Exception e) {
                state.setError(e.getMessage());
                state.setEndTime(OffsetDateTime.now());
                state.setStatus(MigrationStatus.FAILED);
            }
        });

        return "Job started";

    }

    private boolean isStartPossible() {
        return state.getStatus() != MigrationStatus.IN_PROGRESS;
    }

    private Scanner loadFile() throws FileNotFoundException {
        migrationData = ResourceUtils.getFile("classpath:static/fraudTrain.csv");
        Scanner scanner = new Scanner(migrationData);
        scanner.nextLine();
        return scanner;
    }

    private List<String> readBatch(Scanner scanner) {
        List<String> values = new ArrayList<>();
        IntStream.range(0, this.batchSize).forEach( (i) -> {
            if (scanner.hasNext()) {
                values.add(scanner.nextLine());
            }
        });
        return values;
    }

    private TransactionHistory readFromLine(String line) {
        List<String> data = List.of(line.split(","));
        List<String> normDate = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            String s = data.get(i);
            if (s.charAt(0) == '\"') {
                normDate.add(s.replace("\"", "") + data.get(i + 1).replace("\"", ""));
                i++;
            } else {
                normDate.add(s);
            }
        }




        TransactionHistory transaction = new TransactionHistory();
        transaction.setDateTime(normDate.get(1));
        transaction.setCategory(normDate.get(4));
        transaction.setAmount(Float.valueOf(normDate.get(5)));
        transaction.setMigrated(true);
        transaction.setCcNum(normDate.get(2));
        transaction.setMerchantName(normDate.get(3));
        transaction.setUnixTime(OffsetDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(normDate.get(19))), ZoneId.systemDefault()));
        transaction.setTransNum(normDate.get(18));
        transaction.setIsFraud(Objects.equals(normDate.get(22), "1"));


        return transaction;
    }


}
