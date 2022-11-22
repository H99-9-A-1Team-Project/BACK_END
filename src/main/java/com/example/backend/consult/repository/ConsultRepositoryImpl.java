package com.example.backend.consult.repository;

import com.example.backend.global.entity.AnswerState;
import com.example.backend.global.entity.Consult;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.backend.global.entity.QComment.comment;
import static com.example.backend.global.entity.QConsult.*;

@RequiredArgsConstructor
//오버라이딩하는 공간
public class ConsultRepositoryImpl implements ConsultRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Consult> findProductByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(consult)
                .where(consult.user.id.eq(userId),
                        consult.answerState.goe(AnswerState.ANSWER),
                        comment.realtor.id.eq(userId)
                )
                .fetch();
    }
}
