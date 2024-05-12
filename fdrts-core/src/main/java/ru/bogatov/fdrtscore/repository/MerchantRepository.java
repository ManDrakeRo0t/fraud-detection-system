package ru.bogatov.fdrtscore.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bogatov.fdrtscore.model.database.jooq.Tables;
import ru.bogatov.fdrtscore.model.database.jooq.tables.pojos.Merchant;
import ru.bogatov.fdrtscore.model.database.jooq.tables.records.MerchantRecord;
import ru.bogatov.fdrtscore.model.dto.response.PaginationResponse;



import java.util.List;
import java.util.UUID;


import static ru.bogatov.fdrtscore.model.database.jooq.Tables.MERCHANT;

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

    public void trancuateMigrated() {
        context.delete(Tables.MERCHANT).where(Tables.MERCHANT.MIGRATED.eq(true)).execute();
    }

    public Merchant findByName(String name) {
        return context.selectFrom(MERCHANT)
                .where(MERCHANT.NAME.eq(name))
                .fetchOneInto(Merchant.class);
    }

    public Merchant findById(UUID id) {
        return context.selectFrom(MERCHANT)
                .where(MERCHANT.ID.eq(id))
                .fetchOneInto(Merchant.class);
    }

    public void create(Merchant merchant) {
        context.executeInsert(new MerchantRecord(merchant));
    }

    public List<String> getAllNames() {
        return context.select(MERCHANT.NAME)
                .from(MERCHANT)
                .fetchInto(String.class);
    }


    public PaginationResponse<Merchant, String > list(String lastToken, int limit) {
        List<Merchant> data;
        if (lastToken == null) {
            data = context.selectFrom(MERCHANT)
                    .orderBy(MERCHANT.NAME)
                    .limit(limit)
                    .fetchInto(Merchant.class);
        } else {
            data = context.selectFrom(MERCHANT)
                    .orderBy(MERCHANT.NAME)
                    .seek(lastToken)
                    .limit(limit)
                    .fetchInto(Merchant.class);
        }

        return PaginationResponse.<Merchant, String>builder()
                .data(data)
                .lastToken(data.get(data.size() - 1).getName())
                .build();
    }

}
