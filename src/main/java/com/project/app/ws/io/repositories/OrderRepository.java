package com.project.app.ws.io.repositories;

import com.project.app.ws.io.entity.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface OrderRepository extends PagingAndSortingRepository<OrderEntity,Long> {

    OrderEntity findByOrderId(String orderId);
}
