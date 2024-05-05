package ru.bogatov.fdrtscore.service;

import org.springframework.stereotype.Service;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Customer;
import ru.bogatov.fdrtscore.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void batchInsert(List<Customer> customerList) {
        customerRepository.batchInsert(customerList);
    }

    public void trancuate() {
        customerRepository.trancuate();
    }

}
