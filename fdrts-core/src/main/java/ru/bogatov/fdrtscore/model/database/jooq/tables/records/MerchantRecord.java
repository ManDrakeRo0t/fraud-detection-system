/*
 * This file is generated by jOOQ.
 */
package ru.bogatov.fdrtcore.model.database.jooq.tables.records;


import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;

import ru.bogatov.fdrtcore.model.database.jooq.tables.Merchant;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MerchantRecord extends TableRecordImpl<MerchantRecord> implements Record4<UUID, String, Float, Float> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.merchant.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.merchant.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.merchant.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.merchant.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.merchant.lat</code>.
     */
    public void setLat(Float value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.merchant.lat</code>.
     */
    public Float getLat() {
        return (Float) get(2);
    }

    /**
     * Setter for <code>public.merchant.long</code>.
     */
    public void setLong(Float value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.merchant.long</code>.
     */
    public Float getLong() {
        return (Float) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, String, Float, Float> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<UUID, String, Float, Float> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Merchant.MERCHANT.ID;
    }

    @Override
    public Field<String> field2() {
        return Merchant.MERCHANT.NAME;
    }

    @Override
    public Field<Float> field3() {
        return Merchant.MERCHANT.LAT;
    }

    @Override
    public Field<Float> field4() {
        return Merchant.MERCHANT.LONG;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public Float component3() {
        return getLat();
    }

    @Override
    public Float component4() {
        return getLong();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public Float value3() {
        return getLat();
    }

    @Override
    public Float value4() {
        return getLong();
    }

    @Override
    public MerchantRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public MerchantRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public MerchantRecord value3(Float value) {
        setLat(value);
        return this;
    }

    @Override
    public MerchantRecord value4(Float value) {
        setLong(value);
        return this;
    }

    @Override
    public MerchantRecord values(UUID value1, String value2, Float value3, Float value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MerchantRecord
     */
    public MerchantRecord() {
        super(Merchant.MERCHANT);
    }

    /**
     * Create a detached, initialised MerchantRecord
     */
    public MerchantRecord(UUID id, String name, Float lat, Float long_) {
        super(Merchant.MERCHANT);

        setId(id);
        setName(name);
        setLat(lat);
        setLong(long_);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised MerchantRecord
     */
    public MerchantRecord(ru.bogatov.fdrtcore.model.database.jooq.tables.pojos.Merchant value) {
        super(Merchant.MERCHANT);

        if (value != null) {
            setId(value.getId());
            setName(value.getName());
            setLat(value.getLat());
            setLong(value.getLong());
            resetChangedOnNotNull();
        }
    }
}
