package org.springboot.lock.repository;

import org.springboot.lock.domain.OptProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptProductRepository extends JpaRepository<OptProduct, Long> {
}
