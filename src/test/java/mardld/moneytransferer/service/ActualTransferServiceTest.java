package mardld.moneytransferer.service;

import mardld.moneytransferer.MoneyTransfererProperties;
import mardld.moneytransferer.dto.TransferDto;
import org.jooq.ConnectionProvider;
import org.jooq.impl.DSL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ActualTransferServiceTest {

    private ConnectionProvider provider = new SimpleConnectionProvider(new MoneyTransfererProperties());

    @Before
    public void init() {
        clearDatabase();
        insertAccount(1, 10);
        insertAccount(2, 100);
    }

    @Test
    public void transfer() {
        ActualTransferService transferService = new ActualTransferService(provider);

        TransferDto dto = new TransferDto();
        dto.setAccountFrom(1);
        dto.setAccountTo(2);
        dto.setAmount(BigDecimal.TEN);

        // test
        transferService.transfer(dto);

        // verify
        Assert.assertEquals(0, BigDecimal.ZERO        .compareTo(getBalanceById(1)));
        Assert.assertEquals(0, new BigDecimal(110).compareTo(getBalanceById(2)));
    }

    private BigDecimal getBalanceById(long id) {
        return
                new BigDecimal(
                        DSL.using(provider.acquire())
                                .fetchValue("SELECT balance FROM accounts where id = " + id)
                                .toString()
                );
    }

    private void clearDatabase() {
        DSL.using(provider.acquire())
                .execute("delete from accounts");
    }

    private void insertAccount(long id, int balance) {
        DSL.using(provider.acquire())
                .execute("insert into accounts values (" + id + ", " + balance + ", 'EUR')");
    }
}
