package com.project.app.ws.service.Impl;

import com.project.app.ws.exceptions.AddressServiceException;
import com.project.app.ws.exceptions.InvoiceNotFoundException;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.io.entity.AddressEntity;
import com.project.app.ws.io.entity.InvoiceEntity;
import com.project.app.ws.io.entity.UserEntity;
import com.project.app.ws.io.repositories.AddressRepository;
import com.project.app.ws.io.repositories.InvoiceRepository;
import com.project.app.ws.io.repositories.UserRepository;
import com.project.app.ws.service.InvoiceService;
import com.project.app.ws.service.ProductService;
import com.project.app.ws.shared.dto.InvoiceDTO;
import com.project.app.ws.shared.dto.OrderDTO;
import com.project.app.ws.shared.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        UserEntity userEntity = userRepository.findByUserId(invoiceDTO.getUserId());
        if(userEntity == null) throw new UserServiceException("User Not Found");

        AddressEntity addressEntity = addressRepository.findByAddressId(invoiceDTO.getAddressId());
        if(addressEntity == null) throw new AddressServiceException("Address Not Found");

        InvoiceDTO returnValue = new InvoiceDTO();
        Double sum = 0.0;
        for(int i=0; i<invoiceDTO.getOrders().size();i++) {
            OrderDTO orderDTO = invoiceDTO.getOrders().get(i);
            orderDTO.setInvoiceDetails(invoiceDTO);
            ProductDTO productDetails = productService.getProduct(orderDTO.getProductId());
            orderDTO.setProductDetails(productDetails);
            invoiceDTO.getOrders().set(i,orderDTO);
            sum += orderDTO.getQuantity() * productService.getPrice(orderDTO.getProductId());
        }

        InvoiceEntity invoiceEntity = modelMapper.map(invoiceDTO,InvoiceEntity.class);
        invoiceEntity.setUserDetails(userEntity);
        invoiceEntity.setAddressDetails(addressEntity);
        invoiceEntity.setShipped(false);
        invoiceEntity.setDate(new Date());
        invoiceEntity.setTotal(sum);

        InvoiceEntity savedInvoice = invoiceRepository.save(invoiceEntity);

        return modelMapper.map(savedInvoice,InvoiceDTO.class);
    }

    @Override
    public List<InvoiceDTO> getInvoices(String type, int page, int limit) {
        if(page > 0) page--;
        Page<InvoiceEntity> invoicesPage = null;
        Pageable pageableRequest = PageRequest.of(page,limit);
        if(type.equals("shipped")) {
            invoicesPage = invoiceRepository.findAllByShippedTrue(pageableRequest);
        } else if(type.equals("unshipped")) {
            invoicesPage = invoiceRepository.findAllByShippedFalse(pageableRequest);
        } else {    //ALL Invoices
            invoicesPage = invoiceRepository.findAll(pageableRequest);
        }

        List<InvoiceEntity> invoices = invoicesPage.getContent();
        return modelMapper.map(invoices, new TypeToken<List<InvoiceDTO>>() {}.getType());
    }

    @Override
    public InvoiceDTO getInvoice(long invoiceId) {
        InvoiceEntity invoiceEntity = invoiceRepository.findById(invoiceId)
                .orElseThrow(()-> new InvoiceNotFoundException("Invoice Not Found Exception"));

        return modelMapper.map(invoiceEntity,InvoiceDTO.class);
    }
}
