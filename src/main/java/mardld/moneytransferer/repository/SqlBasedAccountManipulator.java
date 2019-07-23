package mardld.moneytransferer.repository;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

import java.math.BigDecimal;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public abstract class SqlBasedAccountManipulator {

    static final Table<Record> ACCOUNTS = table("accounts");
    static final Field<Long> ID = field("id", Long.class);
    static final Field<BigDecimal> BALANCE = field("balance", BigDecimal.class);
    protected static final Field<String> CURRENCY = field("currency", String.class);
}
