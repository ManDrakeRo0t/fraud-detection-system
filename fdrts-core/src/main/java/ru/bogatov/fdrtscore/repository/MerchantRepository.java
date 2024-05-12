package ru.bogatov.fdrtcore.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bogatov.fdrtcore.model.database.jooq.Tables;
import ru.bogatov.fdrtcore.model.database.jooq.tables.pojos.Merchant;
import ru.bogatov.fdrtcore.model.database.jooq.tables.records.MerchantRecord;

import java.util.List;

@Component
public class MerchantRepository {

    @Autowired
    DSLContext context;

    public void batchInsert(List<Merchant> merchants) {
        List<MerchantRecord> records = merchants.stream().map(MerchantRecord::new).toList();
        context.batchInsert(records).executeAsync();
    }

    public void trancuate() {
        context.delete(Tables.MERCHANT).execute();
    }

}
