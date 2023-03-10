package com.projects.ecommerce.repository;

import com.projects.ecommerce.domain.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {

    OrderProduct save(OrderProduct orderProduct);
}