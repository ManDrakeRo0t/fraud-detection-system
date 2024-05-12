package ru.bogatov.fdrtcore.service;

import org.springframework.stereotype.Service;
import ru.bogatov.fdrtcore.model.database.jooq.tables.pojos.Merchant;
import ru.bogatov.fdrtcore.repository.MerchantRepository;

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
