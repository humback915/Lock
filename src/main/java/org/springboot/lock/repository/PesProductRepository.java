package org.springboot.lock.repository;

import org.springboot.lock.domain.PesProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface PesProductRepository extends JpaRepository<PesProduct, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) // 비관적 락 적용 특정 엔티티에 대해 비관적 쓰기 락을 걸어 동시성 문제를 방지합니다.
    Optional<PesProduct> findById(Long id);
}
