package mardld.moneytransferer.transactions;

import org.jooq.DSLContext;

@FunctionalInterface
public interface Transactional {

    void run(DSLContext ctx);
}
