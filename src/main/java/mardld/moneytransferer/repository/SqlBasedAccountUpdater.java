package mardld.moneytransferer.repository;

import mardld.moneytransferer.Account;
import org.jooq.DSLContext;

import javax.inject.Inject;

public class SqlBasedAccountUpdater extends SqlBasedAccountManipulator implements AccountUpdater {

    private final DSLContext context;

    @Inject
    public SqlBasedAccountUpdater(DSLContext context) {
        this.context = context;
    }

    @Override
    public void update(Account account) {
        context.update(ACCOUNTS)
                .set(BALANCE, account.getBalance())
                .where(ID.eq(account.getId()))
                .execute();
    }
}
