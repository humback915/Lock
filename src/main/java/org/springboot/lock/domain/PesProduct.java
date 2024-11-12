package org.springboot.lock.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity
@ToString
public class PesProduct {

    // Getter 및 Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int stock;

    // 기본 생성자
    public PesProduct() {}

    // 생성자
    public PesProduct(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

}
