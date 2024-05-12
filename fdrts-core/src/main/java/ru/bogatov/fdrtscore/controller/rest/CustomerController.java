package ru.bogatov.fdrtscore.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Customer;
import ru.bogatov.fdrtscore.model.dto.response.PaginationResponse;
import ru.bogatov.fdrtscore.service.CustomerService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/{ccNum}")
    public ResponseEntity<Customer> getByCustomerNumber(@PathVariable String ccNum) {
        return  ResponseEntity.ok(customerService.findByCcNum(ccNum));
    }

    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<Customer, LocalDate>> getListCustomers(
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value= "lastToken", required = false) LocalDate lastToken
    ) {
        return  ResponseEntity.ok(customerService.list(lastToken, limit));
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customer));
    }

}
