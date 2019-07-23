package mardld.moneytransferer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import mardld.moneytransferer.config.MoneyTransfererModule;
import mardld.moneytransferer.controller.TransfersController;
import org.flywaydb.core.Flyway;

import static spark.Spark.post;

public class App {

    private final MoneyTransfererProperties properties;
    private final TransfersController transfersController;

    App() {
        Injector injector = Guice.createInjector(new MoneyTransfererModule());
        this.transfersController = injector.getInstance(TransfersController.class);
        this.properties = injector.getInstance(MoneyTransfererProperties.class);
    }

    public static void main(String... args) {
        App app = new App();
        app.initDatabase();
        app.initSparkRoutes();
    }

    void initDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource(properties.getDatabaseUrl(), properties.getDatabaseUser(), properties.getDatabasePassword())
                .load();
        flyway.migrate();
    }

    void initSparkRoutes() {
        post("api/v1/transfers", transfersController::createTransfer);
    }
}
