package org.springboot.lock.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springboot.lock.domain.OptProduct;
import org.springboot.lock.domain.PesProduct;
import org.springboot.lock.repository.OptProductRepository;
import org.springboot.lock.repository.PesProductRepository;
import org.springboot.lock.service.OptProductService;
import org.springboot.lock.service.PesProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private OptProductRepository optProductRepository;

    @Autowired
    private PesProductRepository pesProductRepository;

    @Autowired
    private OptProductService optProductService;

    @Autowired
    private PesProductService pesProductService;

    private Long optProductId;
    private Long pesProductId;

    @BeforeEach
    public void setUp() {
        System.out.println("setUp Start : ");
        /** 낙관적 락 */
        OptProduct optProduct = new OptProduct("Test OptProduct", 10); // 재고 10개 생성
        optProductRepository.save(optProduct);
        optProductId = optProduct.getId();

        /** 비관적 락 */
        PesProduct pesProduct = new PesProduct("Test PesProduct", 10); // 재고 10개 생성
        pesProductRepository.save(pesProduct);
        pesProductId = pesProduct.getId();
        System.out.println("setUp End : ");
    }


    @DisplayName("낙관적 Lock 발생")
    @Test
    public void testOptimistic() throws InterruptedException {
        int numberOfThreads = 2; // 동시에 실행할 스레드 개수
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
//                    System.out.println("Thread " + Thread.currentThread().getName() + " 시작");
                    optProductService.purchaseOptProduct(optProductId);
//                    System.out.println("Thread " + Thread.currentThread().getName() + " 성공");
                } catch (ObjectOptimisticLockingFailureException e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " 충돌 발생: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " 실패: " + e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 끝날 때까지 대기
        executorService.shutdown();

        // 결과 검증
        OptProduct product = optProductRepository.findById(optProductId).orElseThrow();
        System.out.println("result : "+product);
        //assertThat(product.getStock()).isEqualTo(0); // 최종 재고가 0이 되어야 함
        assertThat(product.getStock()).isLessThanOrEqualTo(9); // 재고는 9 이하로 감소해야 함

    }
    @DisplayName("비관적 Lock 발생")
    @Test
    public void testPesimistic() throws InterruptedException {
        int numberOfThreads = 10; // 동시에 실행할 스레드 개수
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        // test //
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    pesProductService.purchaseProduct(pesProductId);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 끝날 때까지 대기
        executorService.shutdown();

        // 결과 검증
        PesProduct product = pesProductRepository.findById(pesProductId).orElseThrow();
        System.out.println("result : "+product);
        assertThat(product.getStock()).isEqualTo(0); // 최종 재고가 0이 되어야 함
    }
}
