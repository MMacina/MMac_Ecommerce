package com.projects.ecommerce.mapper;

import com.projects.ecommerce.domain.Order;
import com.projects.ecommerce.domain.OrderProduct;
import com.projects.ecommerce.domain.Product;
import com.projects.ecommerce.domain.User;
import com.projects.ecommerce.domain.dto.OrderDto;
import com.projects.ecommerce.exception.UserNotFoundException;
import com.projects.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderMapper {

    @Autowired
    UserRepository userRepository;

    public OrderDto mapToOrderDto(Order order) {
        List<Product> listOfProducts = new ArrayList<>();
        for(OrderProduct op : order.getOrderProductSet()){
            for(Long l = 1L; l<=op.getQty(); l++){
                listOfProducts.add(op.getProduct());
            }
        }
        return new OrderDto(
                order.getId(),
                order.getUser().getId(),
                order.getOrderDate(),
                listOfProducts
        );
    }

    public Order mapToOrder(OrderDto orderDto) throws UserNotFoundException {
        Map<Product, Long> counts =
                orderDto.getProductList().stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Set<OrderProduct> orderProductSet = new HashSet<>();
        Order order = new Order(orderDto.getId(), orderDto.getOrderDate(), user);
        for (Map.Entry<Product, Long> entry : counts.entrySet()){
            orderProductSet.add(new OrderProduct(order, entry.getKey(), entry.getKey().getPrice(), entry.getValue()));
        }
        order.setOrderProductSet(orderProductSet);
        return order;
    }

    public List<OrderDto> mapToOrderDtoList(List<Order> orderList) {
        return orderList.stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }
}