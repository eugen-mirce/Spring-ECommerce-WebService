package com.project.app.ws.service;

import com.project.app.ws.shared.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO getOrder(String orderId);
    List<OrderDTO> getOrders(String userId, int page, int limit);
    OrderDTO createOrder(OrderDTO orderDTO);
}
