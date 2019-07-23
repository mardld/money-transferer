package mardld.moneytransferer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    private long accountFrom;
    private long accountTo;
    private BigDecimal amount;
}
