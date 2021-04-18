package com.project.app.ws.service;

import com.project.app.ws.shared.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);
    List<InvoiceDTO> getInvoices(String type, int page, int limit);
    List<InvoiceDTO> getUserInvoices(String userId, int page, int limit);
    InvoiceDTO getInvoice(long invoiceId);
    InvoiceDTO getInvoice(String userId, long invoiceId);
    InvoiceDTO updateInvoice(long invoiceId, InvoiceDTO invoiceDTO);
    void deleteInvoice(long invoiceId);
}
