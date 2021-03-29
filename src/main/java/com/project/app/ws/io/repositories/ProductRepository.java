package com.project.app.ws.io.repositories;

import com.project.app.ws.io.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity,Long> {
    Page<ProductEntity> findAllByCategoryEntity(long categoryId, PageRequest pageableRequest);
}
