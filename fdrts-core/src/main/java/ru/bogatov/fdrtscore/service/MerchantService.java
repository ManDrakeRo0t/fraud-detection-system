package ru.bogatov.fdrtscore.service;

import org.springframework.stereotype.Service;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Customer;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Merchant;
import ru.bogatov.fdrtscore.model.dto.response.PaginationResponse;
import ru.bogatov.fdrtscore.repository.MerchantRepository;

import java.util.List;
import java.util.UUID;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;


    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public void batchInsert(List<Merchant> merchants) {
        merchantRepository.batchInsert(merchants);
    }

    public void trancuate(Boolean migrated) {
        if (migrated) {
            merchantRepository.trancuateMigrated();
        } else {
            merchantRepository.trancuate();
        }
    }

    public Merchant findByName(String name) {
        return merchantRepository.findByName(name);
    }

    public PaginationResponse<Merchant, String> list(String lastToken, Integer limit) {
        return merchantRepository.list(lastToken, limit);
    }

    public Merchant create(Merchant merchant) {
        merchant.setId(UUID.randomUUID());
        merchant.setMigrated(false);
        merchantRepository.create(merchant);
        return merchantRepository.findById(merchant.getId());
    }

    public List<String> getAllNames() {
        return merchantRepository.getAllNames();
    }
}
