package ru.bogatov.fdrtscore.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationResponse<T, V> {
    List<T> data;
    V lastToken;
}
