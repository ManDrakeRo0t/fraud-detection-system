package ru.bogatov.fdrtscore.model.dto.migration;

import lombok.Data;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Customer;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Merchant;

@Data
public class DatasetLine {
    Customer customer;
    Merchant merchant;
}
