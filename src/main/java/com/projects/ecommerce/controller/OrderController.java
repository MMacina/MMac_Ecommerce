package com.projects.ecommerce.controller;

import com.projects.ecommerce.domain.Order;
import com.projects.ecommerce.domain.dto.OrderRequestDto;
import com.projects.ecommerce.domain.dto.OrderDto;
import com.projects.ecommerce.exception.CartNotFoundWhileCreatingOrderException;
import com.projects.ecommerce.exception.OrderNotFoundException;
import com.projects.ecommerce.exception.OrderWithGivenUserNotFoundException;
import com.projects.ecommerce.mapper.OrderMapper;
import com.projects.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orderList = orderService.getALlOrders();
        return ResponseEntity.ok(orderMapper.mapToOrderDtoList(orderList));
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequestDto orderRequestDto) throws CartNotFoundWhileCreatingOrderException {
        orderService.createOrder(orderRequestDto.getCartId());
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto) throws OrderNotFoundException, OrderWithGivenUserNotFoundException {
        Order order = orderService.updateOrder(orderDto);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

    @DeleteMapping(path = "/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}