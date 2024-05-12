package ru.bogatov.fdrtscore.service;

import org.springframework.stereotype.Service;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Customer;
import ru.bogatov.fdrtscore.model.dto.response.PaginationResponse;
import ru.bogatov.fdrtscore.repository.CustomerRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void batchInsert(List<Customer> customerList) {
        customerRepository.batchInsert(customerList);
    }

    public void trancuate(Boolean migrated) {
        if (migrated) {
            customerRepository.trancuateMigrated();
        } else {
            customerRepository.trancuate();
        }
    }

    public Customer findByCcNum(String ccNum) {
        return customerRepository.findByCcNum(ccNum);
    }

    public Customer create(Customer customer) {
        customer.setId(UUID.randomUUID());
        customerRepository.create(customer);
        return customerRepository.findById(customer.getId());
    }

    public List<String> getAllCcNums() {
        return customerRepository.getAllCcNums();
    }

    public List<Customer> getByCcNums(List<String> ccNums) {
        return customerRepository.getByCcNums(ccNums);
    }

    public PaginationResponse<Customer, LocalDate> list(LocalDate lastToken, int limit) {
        return customerRepository.list(lastToken, limit);
    }


}
