package com.projects.ecommerce.service;

import com.projects.ecommerce.domain.*;
import com.projects.ecommerce.domain.dto.OrderDto;
import com.projects.ecommerce.exception.CartNotFoundWhileCreatingOrderException;
import com.projects.ecommerce.exception.OrderNotFoundException;
import com.projects.ecommerce.exception.OrderWithGivenUserNotFoundException;
import com.projects.ecommerce.repository.CartRepository;
import com.projects.ecommerce.repository.OrderProductRepository;
import com.projects.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;

    public List<Order> getALlOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public void createOrder(Long cartId) throws CartNotFoundWhileCreatingOrderException {
        if (cartRepository.findById(cartId).isPresent()) {
            Cart cart = cartRepository.findById(cartId).get();
            Order order = new Order(LocalDate.now(), cart.getUser());
            Set<OrderProduct> orderProductSet = fromListToSet(cart.getProducts(), order);
            order.setOrderProductSet(orderProductSet);
            orderProductRepository.saveAll(orderProductSet);
            orderRepository.save(order);
        } else {
            throw new CartNotFoundWhileCreatingOrderException();
        }
    }

    public Order updateOrder(OrderDto orderDto) throws OrderNotFoundException, OrderWithGivenUserNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(orderDto.getId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getUser().getId() == orderDto.getUserId()){
                order.setOrderDate(orderDto.getOrderDate());
                Set<OrderProduct>set = fromListToSet(orderDto.getProductList(), order);
                order.setOrderProductSet(set);
                orderProductRepository.saveAll(set);
                return orderRepository.save(order);
            } else {
                throw new OrderWithGivenUserNotFoundException();
            }
        } else {
            throw new OrderNotFoundException();
        }
    }

    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new OrderNotFoundException();
        }
    }

    public Set<OrderProduct> fromListToSet(List<Product> list, Order order){
        Set<OrderProduct> set = new HashSet<>();
        Map<Product, Long> counts =
                list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        for (Map.Entry<Product, Long> entry : counts.entrySet()){
            set.add(new OrderProduct(order, entry.getKey(), entry.getKey().getPrice(), entry.getValue()));
        }
        return set;
    }

    public List<Product> fromSetToList(Set<OrderProduct> set){
        List<Product> list = new ArrayList<>();
        for(OrderProduct op : set){
            for(Long l = 1L; l<=op.getQty(); l++){
                list.add(op.getProduct());
            }
        }
        return list;
    }
}