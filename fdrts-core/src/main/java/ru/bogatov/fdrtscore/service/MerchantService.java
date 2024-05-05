package ru.bogatov.fdrtscore.service;

import org.springframework.stereotype.Service;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Merchant;
import ru.bogatov.fdrtscore.repository.MerchantRepository;

import java.util.List;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;


    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public void batchInsert(List<Merchant> merchants) {
        merchantRepository.batchInsert(merchants);
    }

    public void trancuate() {
        merchantRepository.trancuate();
    }
}
