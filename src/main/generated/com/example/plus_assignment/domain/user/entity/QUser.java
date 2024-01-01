package com.example.plus_assignment.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1378402544L;

    public static final QUser user = new QUser("user");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.example.plus_assignment.domain.post.entity.Post, com.example.plus_assignment.domain.post.entity.QPost> postList = this.<com.example.plus_assignment.domain.post.entity.Post, com.example.plus_assignment.domain.post.entity.QPost>createList("postList", com.example.plus_assignment.domain.post.entity.Post.class, com.example.plus_assignment.domain.post.entity.QPost.class, PathInits.DIRECT2);

    public final EnumPath<UserRoleEnum> role = createEnum("role", UserRoleEnum.class);

    public final NumberPath<Long> socialId = createNumber("socialId", Long.class);

    public final EnumPath<com.example.plus_assignment.global.oAuth.SocialType> socialType = createEnum("socialType", com.example.plus_assignment.global.oAuth.SocialType.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

