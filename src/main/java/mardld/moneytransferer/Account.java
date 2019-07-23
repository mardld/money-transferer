package mardld.moneytransferer;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private long id;
    private BigDecimal balance;
    private String currency;
}
