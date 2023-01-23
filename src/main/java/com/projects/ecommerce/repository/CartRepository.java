package com.projects.ecommerce.repository;

import com.projects.ecommerce.domain.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    Cart save(Cart cart);
}