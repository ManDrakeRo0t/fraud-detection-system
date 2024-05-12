package ru.bogatov.fdrtscore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Customer;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Merchant;
import ru.bogatov.fdrtscore.model.dto.migration.DatasetLine;
import ru.bogatov.fdrtscore.model.dto.migration.MigrationResult;
import ru.bogatov.fdrtscore.model.dto.migration.MigrationStatus;
import ru.bogatov.fdrtscore.model.dto.migration.TransactionMigrationDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class MigrationService {

    private final CustomerService customerService;

    private final MerchantService merchantService;

    private final TransactionMigrationSenderService senderService;

    private final MigrationResult state;

    private File migrationData;

    private int batchSize = 100;


    public MigrationService(CustomerService customerService, MerchantService merchantService, TransactionMigrationSenderService senderService) {
        this.customerService = customerService;
        this.merchantService = merchantService;
        this.senderService = senderService;
        state = MigrationResult.builder()
                .status(MigrationStatus.NOT_STARTED)
                .createdCustomers(0)
                .createdMerchants(0)
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
        state.setCreatedCustomers(0);
        state.setCreatedMerchants(0);
        state.setEntitiesCount(0);

        customerService.trancuate();
        merchantService.trancuate();

        Executors.newSingleThreadExecutor().execute(() -> {
            Set<String> createdCustomers = new HashSet<>();
            Set<String> createdMerchants = new HashSet<>();


            try {
                Scanner scanner = loadFile();


                List<String> values = readBatch(scanner);

                while (!values.isEmpty()) {
                    List<Customer> customers = new ArrayList<>();
                    List<Merchant> merchants = new ArrayList<>();
                    List<TransactionMigrationDto> transactions = new ArrayList<>();


                    state.setLinesProcessed(state.getLinesProcessed() + values.size());
                    values.forEach(line -> {
                        DatasetLine data = readFromLine(line);

                        Customer customer = data.getCustomer();
                        Merchant merchant = data.getMerchant();

                        transactions.add(data.getTransaction());

                        if (!createdCustomers.contains(customer.getCcNum())) {
                            createdCustomers.add(customer.getCcNum());
                            customers.add(customer);
                            state.setEntitiesCount(state.getEntitiesCount() + 1);
                            state.setCreatedCustomers(state.getCreatedCustomers() + 1);
                        }

                        if (!createdMerchants.contains(merchant.getName())) {
                            createdMerchants.add(merchant.getName());
                            merchants.add(merchant);
                            state.setEntitiesCount(state.getEntitiesCount() + 1);
                            state.setCreatedMerchants(state.getCreatedMerchants() + 1);
                        }
                    });

                    customerService.batchInsert(customers);
                    merchantService.batchInsert(merchants);

                    //senderService.sendTransactions(transactions);

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

    private DatasetLine readFromLine(String line) {
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


        Merchant merchant = new Merchant();
        merchant.setLat(Float.valueOf(normDate.get(20)));
        merchant.setLong(Float.valueOf(normDate.get(21)));
        merchant.setName(normDate.get(3));
        merchant.setMigrated(true);

        Customer customer = new Customer();
        customer.setCcNum(normDate.get(2));
        customer.setLat(Float.valueOf(normDate.get(13)));
        customer.setLong(Float.valueOf(normDate.get(14)));
        customer.setCity(normDate.get(10));
        customer.setStreet(normDate.get(9));
        customer.setState(normDate.get(11));
        customer.setZip(Integer.valueOf(normDate.get(12)));
        customer.setCityPop(Integer.valueOf(normDate.get(15)));
        customer.setJob(normDate.get(16));
        customer.setBirthday(LocalDate.parse(normDate.get(17)));
        customer.setGender(!Objects.equals(normDate.get(8), "F"));
        customer.setFirstName(normDate.get(6));
        customer.setLastName(normDate.get(7));
        customer.setMigrated(true);

        TransactionMigrationDto transaction = new TransactionMigrationDto();
        transaction.setDateTime(normDate.get(1));
        transaction.setCategory(normDate.get(4));
        transaction.setAmount(Float.valueOf(normDate.get(5)));
        transaction.setMigrated(true);
        transaction.setValidate(false);
        transaction.setCcNum(customer.getCcNum());
        transaction.setMerchantName(merchant.getName());
        transaction.setUnixTime(OffsetDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(normDate.get(19))), ZoneId.systemDefault()));
        transaction.setTransNum(normDate.get(18));
        transaction.setIsFraud(Objects.equals(normDate.get(22), "1"));



        DatasetLine datasetLine = new DatasetLine();

        datasetLine.setCustomer(customer);
        datasetLine.setMerchant(merchant);
        datasetLine.setTransaction(transaction);
        return datasetLine;
    }


}
