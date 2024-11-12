package org.springboot.lock.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPesProduct is a Querydsl query type for PesProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPesProduct extends EntityPathBase<PesProduct> {

    private static final long serialVersionUID = -2106320067L;

    public static final QPesProduct pesProduct = new QPesProduct("pesProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QPesProduct(String variable) {
        super(PesProduct.class, forVariable(variable));
    }

    public QPesProduct(Path<? extends PesProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPesProduct(PathMetadata metadata) {
        super(PesProduct.class, metadata);
    }

}

