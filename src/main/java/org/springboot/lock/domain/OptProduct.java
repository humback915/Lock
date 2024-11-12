package org.springboot.lock.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Setter
@Getter
@Entity
@ToString
public class OptProduct {

    // Getter 및 Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int stock;

    @Version
    private Long version; // 낙관적 락을 위해 버전 필드 추가

    // 기본 생성자
    public OptProduct() {}

    // 생성자
    public OptProduct(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

}
