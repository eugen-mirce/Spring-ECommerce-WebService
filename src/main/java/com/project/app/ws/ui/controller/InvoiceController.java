package com.project.app.ws.ui.controller;

import com.project.app.ws.service.InvoiceService;
import com.project.app.ws.shared.dto.InvoiceDTO;
import com.project.app.ws.ui.model.request.InvoiceRequestModel;
import com.project.app.ws.ui.model.response.InvoiceRest;
import com.project.app.ws.ui.model.response.OperationStatusModel;
import com.project.app.ws.ui.model.response.RequestOperationStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("invoices")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ModelMapper modelMapper;

    /**
     * [GET] Get All Invoices
     * [Path] = http://localhost:8080/app/invoice/{invoiceId}
     * //TODO Add Admin Role Only
     * @param invoiceId
     * @return
     */
    @GetMapping(
            path = { "/{invoiceId", "/{invoiceId}"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public InvoiceRest getInvoice(@PathVariable long invoiceId) {
        InvoiceDTO invoiceDTO = invoiceService.getInvoice(invoiceId);

        return modelMapper.map(invoiceDTO,InvoiceRest.class);
    }

    /**
     * [PUT] Update Invoice [Shipped Status]
     * [Path] = http://localhost:8080/app/invoice/{invoiceId}
     * //TODO Add Admin Role Only
     * @param invoiceId
     * @param invoiceRequestModel
     * @return
     */
    @PutMapping(
            path = { "/{invoiceId}", "/{invoiceId}/" },
            consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
    )
    public InvoiceRest updateInvoice(
            @PathVariable long invoiceId,
            @RequestBody InvoiceRequestModel invoiceRequestModel
    ) {
        InvoiceDTO invoiceDTO = modelMapper.map(invoiceRequestModel,InvoiceDTO.class);
        InvoiceDTO invoice = invoiceService.updateInvoice(invoiceId,invoiceDTO);

        return modelMapper.map(invoice,InvoiceRest.class);
    }

    /**
     * [DELETE] Delete Invoice
     * [Path] = http://localhost:8080/app/invoice/{invoiceId}
     * //TODO Add Admin Role Only
     * @param invoiceId
     * @return
     */
    @DeleteMapping(
            path = { "/{invoiceId}", "/{invoiceId}/"},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel deleteInvoice(@PathVariable long invoiceId) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        invoiceService.deleteInvoice(invoiceId);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    /**
     * [GET] Get All Orders
     * [Path] = http://localhost:8080/app/invoices
     * [Optional] = http://localhost:8080/app/invoices/{type}
     * @param type
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(
            path = { "", "/", "/{type}", "/{type}/"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<InvoiceRest> getInvoices(
            @PathVariable(required = false) String type,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        if(type == null) type = "all";
        List<InvoiceRest> returnValue = new ArrayList<>();
        List<InvoiceDTO> invoices = invoiceService.getInvoices(type,page,limit);
        returnValue = modelMapper.map(invoices, new TypeToken<List<InvoiceRest>>() {}.getType());
        return returnValue;
    }
}
