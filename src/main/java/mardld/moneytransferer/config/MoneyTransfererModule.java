package mardld.moneytransferer.config;

import com.google.inject.AbstractModule;
import mardld.moneytransferer.service.ActualTransferService;
import mardld.moneytransferer.service.SimpleConnectionProvider;
import mardld.moneytransferer.service.TransferService;
import org.jooq.ConnectionProvider;

public class MoneyTransfererModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TransferService.class).to(ActualTransferService.class);
        bind(ConnectionProvider.class).to(SimpleConnectionProvider.class);
    }
}
