package mardld.moneytransferer.service;

import mardld.moneytransferer.dto.TransferDto;
import mardld.moneytransferer.exception.TransferException;

public interface TransferService {

    void transfer(TransferDto transferDto) throws TransferException;
}
