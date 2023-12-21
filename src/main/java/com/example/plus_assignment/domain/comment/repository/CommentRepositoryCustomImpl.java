package com.example.plus_assignment.domain.comment.repository;

import com.example.plus_assignment.domain.comment.entity.Comment;
import com.example.plus_assignment.domain.comment.entity.QComment;
import com.example.plus_assignment.domain.post.entity.Post;
import com.example.plus_assignment.domain.post.entity.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<Comment> getCommentList(final Pageable pageable) {
        QComment comment = QComment.comment;

        BooleanBuilder whereBuilder = new BooleanBuilder();

        JPAQuery<Comment> query =  jpaQueryFactory.selectFrom(comment);

        // 정렬 적용
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                PathBuilder<Post> pathBuilder = new PathBuilder<>(Post.class, comment.getMetadata());
                query.orderBy(new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(order.getProperty(), Comparable.class)));
                //dsl 버젼에 따라 다른가??? 이건 5버젼용

            }
        }


        List<Comment> content = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long totalCount = content.size(); //2번
        //return new PageImpl<>(content, pageable, totalCount);


        return PageableExecutionUtils.getPage(content, pageable, () -> totalCount); // 이게 더 최적화
    }
}
