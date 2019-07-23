package mardld.moneytransferer.transactions;

import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultConnectionProvider;

import java.sql.Connection;

public class TransactionRunner {
    private final boolean silent;
    private final Connection connection;

    public TransactionRunner(Connection connection) {
        this(connection, true);
    }

    private TransactionRunner(Connection connection,
                              boolean silent) {
        this.connection = connection;
        this.silent = silent;
    }

    public void run(Transactional tx) {
        // Initialise some jOOQ objects
        final DefaultConnectionProvider c =
                new DefaultConnectionProvider(connection);
        final Configuration configuration =
                new DefaultConfiguration()
                        .set(c).set(SQLDialect.H2);

        try {
            // Run the transaction and pass a jOOQ
            // DSLContext object to it
            tx.run(DSL.using(configuration));

            // If we get here, then commit the
            // transaction
            c.commit();
        } catch (RuntimeException e) {

            // Any exception will cause a rollback
            c.rollback();
            System.err.println(e.getMessage());

            // Eat exceptions in silent mode.
            if (!silent)
                throw e;
        }
    }
}
