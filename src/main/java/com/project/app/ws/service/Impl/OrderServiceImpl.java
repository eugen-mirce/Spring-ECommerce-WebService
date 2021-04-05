package com.project.app.ws.service.Impl;

import com.project.app.ws.exceptions.AddressServiceException;
import com.project.app.ws.exceptions.OrderServiceException;
import com.project.app.ws.exceptions.ProductServiceException;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.io.entity.AddressEntity;
import com.project.app.ws.io.entity.OrderEntity;
import com.project.app.ws.io.entity.ProductEntity;
import com.project.app.ws.io.entity.UserEntity;
import com.project.app.ws.io.repositories.AddressRepository;
import com.project.app.ws.io.repositories.OrderRepository;
import com.project.app.ws.io.repositories.ProductRepository;
import com.project.app.ws.io.repositories.UserRepository;
import com.project.app.ws.service.OrderService;
import com.project.app.ws.shared.Utils;
import com.project.app.ws.shared.dto.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    Utils utils;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public OrderDTO getOrder(String orderId) {
        OrderEntity order = orderRepository.findByOrderId(orderId);
        if(order == null) {
            throw new OrderServiceException("Order Not Found");
        }

        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getOrders(String userId, int page, int limit) {
        return null;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        UserEntity userEntity = userRepository.findByUserId(orderDTO.getUserId());
        if(userEntity == null) throw new UserServiceException("User Not Found");

        ProductEntity productEntity = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new ProductServiceException("Product Not Found"));

        AddressEntity addressEntity = addressRepository.findByAddressId(orderDTO.getAddressId());
        if(addressEntity == null) throw new AddressServiceException("Address Not Found");

        OrderEntity orderEntity = modelMapper.map(orderDTO,OrderEntity.class);
        orderEntity.setOrderId(utils.generateOrderId(30));
        orderEntity.setUserDetails(userEntity);
        orderEntity.setProductDetails(productEntity);
        orderEntity.setAddressDetails(addressEntity);
        orderEntity.setShipped(Boolean.FALSE);
        orderEntity.setCompleted(Boolean.FALSE);
        orderEntity.setDate(new Date());

        OrderEntity savedOrderDetails = orderRepository.save(orderEntity);

        return modelMapper.map(savedOrderDetails, OrderDTO.class);
    }
}
