package mardld.moneytransferer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mardld.moneytransferer.Account;
import mardld.moneytransferer.dto.TransferDto;
import mardld.moneytransferer.exception.TransferException;
import mardld.moneytransferer.repository.AccountByIdGetter;
import mardld.moneytransferer.repository.AccountUpdater;
import mardld.moneytransferer.repository.SqlBasedAccountByIdGetter;
import mardld.moneytransferer.repository.SqlBasedAccountUpdater;
import mardld.moneytransferer.transactions.TransactionRunner;
import org.jooq.ConnectionProvider;
import org.jooq.tools.StringUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ActualTransferService implements TransferService {

    private final ConnectionProvider connectionProvider;

    @Override
    public void transfer(TransferDto transferDto) throws TransferException {

        long fromId = transferDto.getAccountFrom();
        long toId = transferDto.getAccountTo();

        if (fromId == toId) {
            throw new TransferException("accounts (from/to) in transfer should not be the same");
        }

        try (Connection connection = connectionProvider.acquire()) {
            connection.setAutoCommit(false);
            TransactionRunner runner = new TransactionRunner(connection);
            runner.run(ctx -> {
                AccountByIdGetter accountByIdGetter = new SqlBasedAccountByIdGetter(ctx);
                Account from = accountByIdGetter.getById(fromId).orElseThrow(() -> new TransferException("account not found"));
                Account to = accountByIdGetter.getById(toId).orElseThrow(() -> new TransferException("account not found"));

                BigDecimal transferAmount = transferDto.getAmount();
                if (from.getBalance().compareTo(transferAmount) < 0) {
                    throw new TransferException("insufficient balance in account " + fromId);
                }

                if (!StringUtils.equals(from.getCurrency(), to.getCurrency())) {
                    throw new TransferException("transactions supported on accounts with same currency");
                }

                from.setBalance(from.getBalance().subtract(transferAmount));
                to.setBalance(to.getBalance().add(transferAmount));

                AccountUpdater accountUpdater = new SqlBasedAccountUpdater(ctx);
                accountUpdater.update(from);
                accountUpdater.update(to);
            });
        } catch (SQLException e) {
            log.error("Sql connection error: {}", e.getMessage());
        }

    }

}
