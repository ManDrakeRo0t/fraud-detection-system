package ru.bogatov.fdrtcore.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import ru.bogatov.fdrtcore.model.database.jooq.Tables;
import ru.bogatov.fdrtcore.model.database.jooq.tables.pojos.Customer;
import ru.bogatov.fdrtcore.model.database.jooq.tables.records.CustomerRecord;

import java.util.List;


@Component
public class CustomerRepository {


    private final DSLContext context;

    public CustomerRepository(DSLContext context) {
        this.context = context;
    }

    public void batchInsert(List<Customer> customerList) {
        List<CustomerRecord> records = customerList.stream().map(CustomerRecord::new).toList();
        context.batchInsert(records).executeAsync();
    }

    public void trancuate() {
        context.delete(Tables.CUSTOMER).execute();
    }


}
