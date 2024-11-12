package org.springboot.lock.service;

import org.springboot.lock.domain.PesProduct;
import org.springboot.lock.repository.PesProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesProductService {
    private final PesProductRepository pesProductRepository;

    public PesProductService(PesProductRepository pesProductRepository) {
        this.pesProductRepository = pesProductRepository;
    }

    @Transactional
    public void purchaseProduct(Long productId) {
        // 비관적 락 적용하여 상품 조회
        PesProduct product = pesProductRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("상품을 찾을 수 없습니다."));
        // 재고가 있는지 확인
        if (product.getStock() > 0) {
            // 재고 감소
            product.setStock(product.getStock() - 1);
            // 엔티티 저장
            PesProduct savedProduct = pesProductRepository.save(product);
            System.out.println("재고 감소 성공 : "+savedProduct.getStock());
        } else {
            throw new IllegalStateException("재고가 부족합니다.");
        }
    }
}
