package com.example.plus_assignment.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthEmail is a Querydsl query type for AuthEmail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthEmail extends EntityPathBase<AuthEmail> {

    private static final long serialVersionUID = 1140569775L;

    public static final QAuthEmail authEmail = new QAuthEmail("authEmail");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isChecked = createBoolean("isChecked");

    public QAuthEmail(String variable) {
        super(AuthEmail.class, forVariable(variable));
    }

    public QAuthEmail(Path<? extends AuthEmail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthEmail(PathMetadata metadata) {
        super(AuthEmail.class, metadata);
    }

}

