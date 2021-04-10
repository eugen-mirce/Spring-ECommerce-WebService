package com.project.app.ws.io.repositories;

import com.project.app.ws.io.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity,Long> {
    Page<InvoiceEntity> findAllByShippedTrue(Pageable pageableRequest);
    Page<InvoiceEntity> findAllByShippedFalse(Pageable pageableRequest);
}
