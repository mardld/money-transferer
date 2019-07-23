package mardld.moneytransferer.repository;

import mardld.moneytransferer.Account;

import java.util.Optional;

public interface AccountByIdGetter {

    Optional<Account> getById(long id);
}
