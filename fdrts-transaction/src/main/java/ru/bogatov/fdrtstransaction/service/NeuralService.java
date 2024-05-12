package ru.bogatov.fdrtstransaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bogatov.fdrtstransaction.model.FraudCheckRequest;
import ru.bogatov.fdrtstransaction.model.FraudCheckResult;
import ru.bogatov.fdrtstransaction.model.TransactionEvent;

import java.time.OffsetDateTime;

@Service
public class NeuralServiceClient {

    @Autowired
    WebClient neuralServiceClient;

    public FraudCheckResult checkTransaction(TransactionEvent event) {
        return neuralServiceClient.post()
                .uri("/api/network")
                .bodyValue(transformPayload(event))
                .retrieve()
                .bodyToMono(FraudCheckResult.class)
                .block();
    }

    private FraudCheckRequest transformPayload(TransactionEvent event) {
        FraudCheckRequest request = new FraudCheckRequest();
        request.setCategory(event.getCategory());
        request.setAmount(event.getAmount());
        request.setCityPop(event.getCityPop());
        request.setMerchLat(event.getMerchLat());
        request.setCcLong(event.getCcLong());
        request.setCcLat(event.getCcLat());
        request.setMerchLong(event.getMerchLong());
        request.setUnixTime(event.getUnixTime().toEpochSecond());

        OffsetDateTime time = event.getUnixTime();

        request.setTransDay(time.getDayOfMonth());
        request.setTransHour(time.getHour());
        request.setTransMouth(time.getMonthValue());

        return request;
    }


}
