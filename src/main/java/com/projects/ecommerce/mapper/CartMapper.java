package com.projects.ecommerce.mapper;

import com.projects.ecommerce.domain.Cart;
import com.projects.ecommerce.domain.User;
import com.projects.ecommerce.domain.dto.CartDto;
import com.projects.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CartMapper {

    private UserRepository userRepository;

    public CartDto mapToCartDto(Cart cart) {
        return new CartDto(cart.getUser().getId(), cart.getProducts());
    }

    public Cart mapToCart(CartDto cartDto) {
        User user = userRepository.findById(cartDto.getUserId()).get();
        return new Cart(user, cartDto.getProducts());
    }
}