package mardld.moneytransferer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mardld.moneytransferer.dto.TransferDto;
import mardld.moneytransferer.exception.TransferException;
import mardld.moneytransferer.service.TransferService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class TransfersController {

    private final TransferService transferService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createTransfer(Request request, Response response) {
        try {
            TransferDto transferDto = objectMapper.readValue(request.bodyAsBytes(), TransferDto.class);
            transferService.transfer(transferDto);
            response.status(200);
            return "done";
        } catch (TransferException | IOException ex) {
            log.error(ex.getMessage(), ex);
            response.status(500);
            return ex.getMessage();
        }
    }
}
