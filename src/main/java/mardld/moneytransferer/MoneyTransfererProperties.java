package mardld.moneytransferer;

import lombok.Data;

@Data
public class MoneyTransfererProperties {
    private String databaseUrl = "jdbc:h2:~/databasefile;AUTO_SERVER=TRUE";
    private String databaseUser = "sa";
    private String databasePassword;
}
