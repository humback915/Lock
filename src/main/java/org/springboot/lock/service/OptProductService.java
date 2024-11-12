package org.springboot.lock.service;

import org.springboot.lock.domain.OptProduct;
import org.springboot.lock.repository.OptProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.OptimisticLockException;

@Service
public class OptProductService {
    private final OptProductRepository optProductRepository;

    public OptProductService(OptProductRepository optProductRepository) {
        this.optProductRepository = optProductRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW) // 항상 새로운 트랜잭션을 시작하며, 기존 트랜잭션과는 독립적으로 실행됩니다.
    public void purchaseOptProduct(Long optProductId) {
        try {
            // 상품 조회
            OptProduct optProduct = optProductRepository.findById(optProductId).orElseThrow(()->new IllegalArgumentException("상품을 찾을 수 없습니다."));
            System.out.println("상품 조회 성공 : "+optProduct);
            // 재고가 있는지 확인
            if (optProduct.getStock() > 0) {
                // 재고 감소
                optProduct.setStock(optProduct.getStock() - 1);
                // 엔티티 저장
                OptProduct savedProduct = optProductRepository.save(optProduct);
                System.out.println("재고 감소 성공 : "+savedProduct.getStock());
            } else {
                throw new IllegalStateException("재고가 부족합니다.");
            }
        } catch (OptimisticLockException e) {
            throw new RuntimeException("낙관적 락 충돌 발생! 다시 시도해주세요.",e);
        }
    }
}
