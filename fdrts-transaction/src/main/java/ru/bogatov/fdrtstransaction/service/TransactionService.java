package ru.bogatov.fdrtstransaction.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.bogatov.fdrtstransaction.model.TransactionEvent;
import ru.bogatov.fdrtstransaction.model.database.jooq.tables.pojos.TransactionHistory;
import ru.bogatov.fdrtstransaction.repository.TransactionRepository;

import java.util.List;

import static ru.bogatov.fdrtstransaction.config.WebSocketConfig.TOPIC_DESTINATION_PREFIX;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    NeuralService neuralServiceClient;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    public void create(TransactionHistory transactionHistory) {
        transactionRepository.insert(transactionHistory);
    }

    public void trancuate(boolean migrated) {
        if (migrated) {
            transactionRepository.trancuateMigrated();
        } else {
            transactionRepository.trancuate();
        }
    }

    public void batchInsert(List<TransactionHistory> list) {
        transactionRepository.batchInsert(list);
    }

    public void handleTransactionEvent(String message) {
        try {
            TransactionEvent transactionDto = objectMapper.readValue(message, TransactionEvent.class);

            TransactionHistory transaction = fromDto(transactionDto);

            if (transactionDto.getValidate()) {
                transaction.setIsFraud(neuralServiceClient.checkTransaction(transactionDto).getIsFraud());
                transactionDto.setIsFraud(transaction.getIsFraud());
            } else {
                transaction.setIsFraud(null);
                transactionDto.setIsFraud(null);
            }

            messagingTemplate.convertAndSend(getFetchEventsDestination(),
                    transactionDto
                    );

            transactionRepository.insert(transaction);

        } catch (Exception e) {
            log.error("Error during processing transaction : {}, error : {}", message, e.getMessage());
        }
    }

    private TransactionHistory fromDto(TransactionEvent dto) {
        TransactionHistory th = new TransactionHistory();
        th.setId(dto.getId());
        th.setAmount(dto.getAmount());
        th.setCategory(dto.getCategoryName());
        th.setCcNum(dto.getCcNum());
        th.setDateTime(dto.getDateTime());
        th.setMigrated(dto.getMigrated());
        th.setMerchantName(dto.getMerchantName());
        th.setUnixTime(dto.getUnixTime());
        th.setTransNum(dto.getTransNum());
        return th;
    }

    public static String getFetchEventsDestination() {
        return TOPIC_DESTINATION_PREFIX + "events.transactions";
    }
}
