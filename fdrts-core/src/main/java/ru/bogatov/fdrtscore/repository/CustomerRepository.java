package ru.bogatov.fdrtscore.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import ru.bogatov.fdrtscore.model.database.jooq.Tables;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Customer;
import ru.bogatov.fdrtscore.model.database.jooq.tables.records.CustomerRecord;
import ru.bogatov.fdrtscore.model.dto.response.PaginationResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static ru.bogatov.fdrtscore.model.database.jooq.Tables.CUSTOMER;


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
        context.delete(CUSTOMER).execute();
    }

    public Customer findByCcNum(String ccNum) {
        return context.selectFrom(CUSTOMER)
                .where(CUSTOMER.CC_NUM.eq(ccNum))
                .fetchOneInto(Customer.class);
    }

    public Customer findById(UUID id) {
        return context.selectFrom(CUSTOMER)
                .where(CUSTOMER.ID.eq(id))
                .fetchOneInto(Customer.class);
    }

    public List<Customer> getByCcNums(List<String> ccNums) {
        return context.select(CUSTOMER)
                .where(CUSTOMER.CC_NUM.in(ccNums))
                .fetchInto(Customer.class);
    }

    public void create(Customer customer) {
        context.executeInsert(new CustomerRecord(customer));
    }

    public List<String> getAllCcNums() {
        return context.select(CUSTOMER.CC_NUM)
                .from(CUSTOMER)
                .fetchInto(String.class);
    }

    public void trancuateMigrated() {
        context.delete(CUSTOMER).where(Tables.CUSTOMER.MIGRATED.eq(true)).execute();
    }

    public PaginationResponse<Customer, LocalDate> list(LocalDate lastToken, int limit) {
        List<Customer> data;
        if (lastToken == null) {
            data = context.selectFrom(CUSTOMER)
                    .orderBy(CUSTOMER.BIRTHDAY)
                    .limit(limit)
                    .fetchInto(Customer.class);
        } else {
            data = context.selectFrom(CUSTOMER)
                    .orderBy(CUSTOMER.BIRTHDAY)
                    .seek(lastToken)
                    .limit(limit)
                    .fetchInto(Customer.class);
        }

        return PaginationResponse.<Customer, LocalDate>builder()
                .data(data)
                .lastToken(data.get(data.size() - 1).getBirthday())
                .build();
    }


}
