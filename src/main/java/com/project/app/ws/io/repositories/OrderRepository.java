package com.project.app.ws.io.repositories;

import com.project.app.ws.io.entity.OrderEntity;
import com.project.app.ws.shared.dto.OrderDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity,Long> {
    //OrderDTO createOrder(OrderDTO orderDTO);
}
