package ru.bogatov.fdrtstransaction.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bogatov.fdrtstransaction.model.database.jooq.Tables;
import ru.bogatov.fdrtstransaction.model.database.jooq.tables.pojos.TransactionHistory;
import ru.bogatov.fdrtstransaction.model.database.jooq.tables.records.TransactionHistoryRecord;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionRepository {

    @Autowired
    DSLContext context;

    public void insert(TransactionHistory transactionHistory) {
        context.executeInsert(new TransactionHistoryRecord(transactionHistory));
    }

    public void batchInsert(List<TransactionHistory> transactionHistory) {
        List<TransactionHistoryRecord> records = transactionHistory.stream().map(TransactionHistoryRecord::new).toList();
        context.batchInsert(records).execute();
    }

    public void trancuate() {
        context.delete(Tables.TRANSACTION_HISTORY).execute();
    }

    public void trancuateMigrated() {
        context.delete(Tables.TRANSACTION_HISTORY).where(Tables.TRANSACTION_HISTORY.MIGRATED.eq(true)).execute();
    }

    public List<TransactionHistory> getCustomerTransactions(String ccNum) {
        return context.selectFrom(Tables.TRANSACTION_HISTORY)
                .where(Tables.TRANSACTION_HISTORY.CC_NUM.eq(ccNum))
                .fetchInto(TransactionHistory.class);
    }

    public List<TransactionHistory> getMerchantTransactions(String name) {
        return context.selectFrom(Tables.TRANSACTION_HISTORY)
                .where(Tables.TRANSACTION_HISTORY.MERCHANT_NAME.eq(name))
                .fetchInto(TransactionHistory.class);
    }

    public List<TransactionHistory> findByCcNum(String ccNum) {
        return context.selectFrom(Tables.TRANSACTION_HISTORY)
                .where(Tables.TRANSACTION_HISTORY.CC_NUM.eq(ccNum))
                .orderBy(Tables.TRANSACTION_HISTORY.UNIX_TIME.desc())
                .fetchInto(TransactionHistory.class);
    }

    public List<TransactionHistory> findByMerchantName(String merchantName) {
        return context.selectFrom(Tables.TRANSACTION_HISTORY)
                .where(Tables.TRANSACTION_HISTORY.MERCHANT_NAME.eq(merchantName))
                .orderBy(Tables.TRANSACTION_HISTORY.UNIX_TIME.desc())
                .fetchInto(TransactionHistory.class);
    }

    public TransactionHistory getById(UUID id) {
        return context.selectFrom(Tables.TRANSACTION_HISTORY)
                .where(Tables.TRANSACTION_HISTORY.ID.eq(id))
                .fetchOneInto(TransactionHistory.class);
    }




}
