package ru.bogatov.fdrtscore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bogatov.fdrtscore.model.TransactionProperties;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Customer;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Merchant;
import ru.bogatov.fdrtscore.model.dto.TransactionEvent;
import ru.bogatov.fdrtscore.model.dto.migration.DatasetLine;
import ru.bogatov.fdrtscore.model.dto.request.TransactionEmulationRequest;
import ru.bogatov.fdrtscore.model.dto.request.TransactionRequest;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    CustomerService customerService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    TransactionSenderService senderService;

    Map<String, Integer> categoriesMapping = new HashMap<>();

    TransactionProperties fraudProperties;

    TransactionProperties legitProperties;

    {
        categoriesMapping.put("misc_net", 0);
        categoriesMapping.put("grocery_pos", 1);
        categoriesMapping.put("entertainment", 2);
        categoriesMapping.put("gas_transport", 3);
        categoriesMapping.put("misc_pos", 4);
        categoriesMapping.put("grocery_net", 5);
        categoriesMapping.put("shopping_net", 6);
        categoriesMapping.put("shopping_pos", 7);
        categoriesMapping.put("food_dining", 8);
        categoriesMapping.put("personal_care", 9);
        categoriesMapping.put("health_fitness", 10);
        categoriesMapping.put("travel", 11);
        categoriesMapping.put("kids_pets", 12);
        categoriesMapping.put("home", 13);

        fraudProperties = TransactionProperties.builder()
                .categories(List.of("grocery_pos", "misc_net", "grocery_pos"))
                .from(OffsetDateTime.parse("2024-05-12T22:00:00+00:00"))
                .duration(1)
                .minAmount(20f)
                .maxAmount(600f)
                .build();

        legitProperties = TransactionProperties.builder()
                .categories(List.of("travel", "home", "gas_transport"))
                .from(OffsetDateTime.parse("2024-05-12T04:00:00+00:00"))
                .duration(16)
                .minAmount(1000f)
                .maxAmount(9000f)
                .build();
    }


    public Void startEmulation(TransactionEmulationRequest request) {

        Executors.newSingleThreadExecutor().execute(() -> {
            List<String> randomCustomers = prepareRandomValues(request, customerService.getAllCcNums());

            List<String> randomMerchants = prepareRandomValues(request, merchantService.getAllNames());

            int fraudTransactionsCount = (int) (((float) request.getTransactionsAmount() / 100) * request.getFraudPercent());

            int legitTransactionsCount = request.getTransactionsAmount() - fraudTransactionsCount;

            List<TransactionRequest> requests = new ArrayList<>();

            IntStream.range(0, fraudTransactionsCount).forEach((i) -> {

                String ccNum = i >= randomCustomers.size() ? randomCustomers.get(i % randomCustomers.size()) : randomCustomers.get(i);
                String merchName = i >= randomMerchants.size() ? randomMerchants.get(i % randomMerchants.size()) : randomMerchants.get(i);

                requests.add(createRequest(ccNum, merchName, fraudProperties));
            });

            IntStream.range(0, legitTransactionsCount).forEach((i) -> {

                String ccNum = i >= randomCustomers.size() ? randomCustomers.get(i % randomCustomers.size()) : randomCustomers.get(i);
                String merchName = i >= randomMerchants.size() ? randomMerchants.get(i % randomMerchants.size()) : randomMerchants.get(i);

                requests.add(createRequest(ccNum, merchName, legitProperties));
            });

            Collections.shuffle(requests);

            requests.forEach(transactionRequest -> {
                try {
                    if (request.getDelay() != null) {
                        Thread.sleep(request.getDelay());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    sendTransactionEvent(transactionRequest);
                }
            });
        });

        return null;
    }

    public List<String> prepareRandomValues(TransactionEmulationRequest request, List<String> allData) {

        if (request.getTransactionsAmount() >= allData.size()) {
            return allData;
        }

        Set<String> selectedData = new HashSet<>();

        Random random = new Random();

        while (selectedData.size() != request.getTransactionsAmount()) {
            selectedData.add(allData.get(random.nextInt(allData.size())));
        }

        return selectedData.stream().toList();
    }

    public List<String> getCategories() {
        return categoriesMapping.keySet().stream().toList();
    }

    public UUID sendTransactionEvent(TransactionRequest transactionRequest) {
        DatasetLine datasetLine = validateTransaction(transactionRequest);

        TransactionEvent transactionEvent = new TransactionEvent();

        transactionEvent.setId(UUID.randomUUID());
        transactionEvent.setValidate(transactionRequest.getValidate());
        transactionEvent.setMigrated(false);
        transactionEvent.setCcNum(datasetLine.getCustomer().getCcNum());
        transactionEvent.setCcLat(datasetLine.getCustomer().getLat());
        transactionEvent.setCcLong(datasetLine.getCustomer().getLong());
        transactionEvent.setMerchantName(datasetLine.getMerchant().getName());
        transactionEvent.setMerchLat(datasetLine.getMerchant().getLat());
        transactionEvent.setMerchLong(datasetLine.getMerchant().getLong());
        transactionEvent.setCityPop(datasetLine.getCustomer().getCityPop());
        transactionEvent.setAmount(transactionRequest.getAmount());
        transactionEvent.setCategory(categoriesMapping.get(transactionRequest.getCategory()));
        transactionEvent.setCategoryName(transactionRequest.getCategory());
        if (transactionRequest.getDateTime() != null) {
            transactionEvent.setUnixTime(transactionRequest.getDateTime());
        } else {
            transactionEvent.setUnixTime(OffsetDateTime.now());
        }
        transactionEvent.setDateTime(LocalDate.now().toString());
        transactionEvent.setTransNum(UUID.randomUUID().toString().replace("-", ""));

        senderService.sendTransactionEvent(transactionEvent);

        return transactionEvent.getId();
    }


    public DatasetLine validateTransaction(TransactionRequest request) {
        Customer customer = customerService.findByCcNum(request.getCcNum());

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        Merchant merchant = merchantService.findByName(request.getMerchantName());

        if (merchant == null) {
            throw new RuntimeException("Merchant not found");
        }

        if (!categoriesMapping.containsKey(request.getCategory())) {
            throw new RuntimeException("Category not found");
        }

        DatasetLine datasetLine = new DatasetLine();
        datasetLine.setMerchant(merchant);
        datasetLine.setCustomer(customer);

        return datasetLine;
    }

    private TransactionRequest createRequest(String ccNum, String merchName, TransactionProperties properties) {
        Random random = new Random();

        TransactionRequest request = new TransactionRequest();
        request.setValidate(true);
        request.setCcNum(ccNum);
        request.setMerchantName(merchName);
        request.setCategory(properties.getCategories().get(random.nextInt(3)));
        request.setAmount(random.nextFloat(properties.getMinAmount(), properties.getMaxAmount()));
        request.setDateTime(properties.getFrom().plusHours(random.nextInt(properties.getDuration())));

        return request;
    }


}
