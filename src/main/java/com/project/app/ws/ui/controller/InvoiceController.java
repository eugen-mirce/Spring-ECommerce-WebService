package com.project.app.ws.ui.controller;

import com.project.app.ws.service.InvoiceService;
import com.project.app.ws.shared.dto.InvoiceDTO;
import com.project.app.ws.ui.model.response.InvoiceRest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("invoice")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            path = { "/{invoiceId", "/{invoiceId}"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public InvoiceRest getInvoice(@PathVariable long invoiceId) {
        InvoiceDTO invoiceDTO = invoiceService.getInvoice(invoiceId);

        return modelMapper.map(invoiceDTO,InvoiceRest.class);
    }


}

@RestController
@RequestMapping("invoices")
class InvoicesController {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ModelMapper modelMapper;

    /**
     * [GET] Get All Orders
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