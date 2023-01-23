package com.projects.ecommerce.controller;

import com.projects.ecommerce.domain.Cart;
import com.projects.ecommerce.domain.Order;
import com.projects.ecommerce.domain.dto.CartDto;
import com.projects.ecommerce.domain.dto.OrderDto;
import com.projects.ecommerce.exception.CartNotFoundException;
import com.projects.ecommerce.exception.CartNotFoundWhileCreatingOrderException;
import com.projects.ecommerce.exception.ProductNotFoundException;
import com.projects.ecommerce.exception.UserNotFoundException;
import com.projects.ecommerce.mapper.CartMapper;
import com.projects.ecommerce.mapper.OrderMapper;
import com.projects.ecommerce.service.CartDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartDbService cartDbService;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCart(@RequestBody CartDto cartDto) throws UserNotFoundException {
        cartDbService.createCart(cartDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable long cartId) throws CartNotFoundException {
        Cart cart = cartDbService.getCart(cartId);
        CartDto cartDto = cartMapper.mapToCartDto(cart);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/add/{cartId}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable long cartId, @RequestParam long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart updatedCart = cartDbService.addProductToCart(cartId, productId);
        CartDto cartDto = cartMapper.mapToCartDto(updatedCart);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/remove")
    public ResponseEntity<CartDto> removeProductFromCart(@RequestParam long cartId, @RequestParam long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart updatedCart = cartDbService.removeProductFromCart(cartId, productId);
        CartDto cartDto = cartMapper.mapToCartDto(updatedCart);
        return ResponseEntity.ok(cartDto);
     }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestParam long cartId) throws CartNotFoundWhileCreatingOrderException {
        Order newOrder = cartDbService.createOrder(cartId);
        OrderDto orderDto = orderMapper.mapToOrderDto(newOrder);
        return ResponseEntity.ok(orderDto);
    }
}