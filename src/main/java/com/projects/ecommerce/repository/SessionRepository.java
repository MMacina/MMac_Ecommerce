package com.projects.ecommerce.repository;

import com.projects.ecommerce.domain.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
}