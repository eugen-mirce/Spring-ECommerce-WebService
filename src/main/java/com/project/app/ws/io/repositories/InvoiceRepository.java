package com.project.app.ws.io.repositories;

import com.project.app.ws.io.entity.InvoiceEntity;
import com.project.app.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity,Long> {
    Page<InvoiceEntity> findAllByShippedTrue(Pageable pageableRequest);
    Page<InvoiceEntity> findAllByShippedFalse(Pageable pageableRequest);
    Page<InvoiceEntity> findAllByUserDetails(UserEntity userEntity, Pageable pageableRequest);
    InvoiceEntity findByIdAndUserDetails(long invoiceId, UserEntity userEntity);
}
