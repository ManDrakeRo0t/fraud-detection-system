package ru.bogatov.fdrtstransaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FraudCheckResult {
    @JsonProperty("is_fraud")
    Boolean isFraud;
}
