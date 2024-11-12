package org.springboot.lock.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOptProduct is a Querydsl query type for OptProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptProduct extends EntityPathBase<OptProduct> {

    private static final long serialVersionUID = 1425841160L;

    public static final QOptProduct optProduct = new QOptProduct("optProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QOptProduct(String variable) {
        super(OptProduct.class, forVariable(variable));
    }

    public QOptProduct(Path<? extends OptProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOptProduct(PathMetadata metadata) {
        super(OptProduct.class, metadata);
    }

}

