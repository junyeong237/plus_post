package com.example.plus_assignment.domain.post.repository;

import static com.example.plus_assignment.domain.post.entity.QPost.post;
import static com.querydsl.core.types.ExpressionUtils.orderBy;

import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.entity.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostListWithPage(Pageable pageable) {
        QPost post = QPost.post;

        JPAQuery<Post> query =  jpaQueryFactory.selectFrom(post);


        // 정렬 적용
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                PathBuilder<Post> pathBuilder = new PathBuilder<>(Post.class, post.getMetadata());
                query.orderBy(new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(order.getProperty(), Comparable.class)));
                //dsl 버젼에 따라 다른가??? 이건 5버젼용

            }
        }

          List<Post> postList = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return postList;
    }

    @Override
    public Page<Post> searchPostListWithSearchAndPaging(final String search, final Pageable pageable) {
        QPost post = QPost.post;

        BooleanBuilder whereBuilder = new BooleanBuilder();

        whereBuilder.or(post.title.like("%" + search + "%"))
            .or(post.content.contains(search));


        BooleanExpression whereClause = buildWhereClause(search);

        JPAQuery<Post> query =  jpaQueryFactory.selectFrom(post);

        query.where(whereClause);

        // 정렬 적용
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                PathBuilder<Post> pathBuilder = new PathBuilder<>(Post.class, post.getMetadata());
                query.orderBy(new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(order.getProperty(), Comparable.class)));
                //dsl 버젼에 따라 다른가??? 이건 5버젼용

            }
        }

        List<Post> content = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long totalCount = content.size(); //2번 offset내에 있는 갯수
        //return new PageImpl<>(content, pageable, totalCount);
        //slice?


        return PageableExecutionUtils.getPage(content, pageable, () -> totalCount); // 이게 더 최적화

    }



    private BooleanExpression buildWhereClause(String search) {
        BooleanExpression whereExpression = null;

        if (search != null && !search.isEmpty()) {
            whereExpression = post.title.like("%" + search + "%")
                .or(post.content.contains(search));
        }

        return whereExpression;
    }

}
