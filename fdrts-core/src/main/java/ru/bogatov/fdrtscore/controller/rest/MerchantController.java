package ru.bogatov.fdrtscore.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Merchant;
import ru.bogatov.fdrtscore.model.dto.response.PaginationResponse;
import ru.bogatov.fdrtscore.service.MerchantService;



@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @GetMapping("/{name}")
    public ResponseEntity<Merchant> getByCustomerNumber(@PathVariable String name) {
        return  ResponseEntity.ok(merchantService.findByName(name));
    }

    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<Merchant, String>> getListCustomers(
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value= "lastToken", required = false) String lastToken
    ) {
        return  ResponseEntity.ok(merchantService.list(lastToken, limit));
    }

    @PostMapping
    public ResponseEntity<Merchant> create(@RequestBody Merchant merchant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(merchantService.create(merchant));
    }


}
