package com.projects.ecommerce.repository;

import com.projects.ecommerce.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    Product save(Product product);

    @Override
    List<Product> findAll();

    List<Product> findByNameIgnoreCase(String name);
}