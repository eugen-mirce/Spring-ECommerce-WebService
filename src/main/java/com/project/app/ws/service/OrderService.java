package com.project.app.ws.service;

import com.project.app.ws.shared.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO getOrder(long id);
    OrderDTO createOrder(OrderDTO orderDTO);
}
