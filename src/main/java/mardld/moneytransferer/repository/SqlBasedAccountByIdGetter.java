package mardld.moneytransferer.repository;

import mardld.moneytransferer.Account;
import org.jooq.DSLContext;

import javax.inject.Inject;
import java.util.Optional;

public class SqlBasedAccountByIdGetter extends SqlBasedAccountManipulator implements AccountByIdGetter {

    private final DSLContext context;

    @Inject
    public SqlBasedAccountByIdGetter(DSLContext context) {
        this.context = context;
    }

    @Override
    public Optional<Account> getById(long id) {
        return
                context.select(ID, BALANCE, CURRENCY)
                        .from(ACCOUNTS)
                        .where(ID.eq(id)).fetchStreamInto(Account.class).findFirst();
    }
}
