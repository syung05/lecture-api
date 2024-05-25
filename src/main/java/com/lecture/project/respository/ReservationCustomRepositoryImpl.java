package com.lecture.project.respository;

import com.lecture.project.dto.QReservationResponse;
import com.lecture.project.dto.ReservationResponse;
import com.lecture.project.dto.ReservationSearch;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.lecture.project.model.QReservation.reservation;

@Repository
@RequiredArgsConstructor
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReservationResponse> findReservations(ReservationSearch search) {

        return jpaQueryFactory.select(new QReservationResponse(reservation))
                .from(reservation)
                .where(lectureIdEqual(search.getLectureId()),
                        participantEqual(search.getParticipant()))
                .fetch();
    }

    @Override
    public List<ReservationResponse> findPopularReservations() {
        StringPath aliasCount = Expressions.stringPath("lectureCount");

        LocalDateTime startDate = LocalDateTime.now().minusDays(3);

        return jpaQueryFactory.select(new QReservationResponse(reservation.lecture, reservation.lecture.count().as("lectureCount")))
                .from(reservation)
                .where(reservation.createdDate.after(startDate))
                .orderBy(aliasCount.desc())
                .groupBy(reservation.lecture)
                .fetch();
    }


    private Predicate participantEqual(String participant) {
        return StringUtils.hasText(participant) ? reservation.participant.contains(participant) : null;
    }

    private BooleanExpression lectureIdEqual(Long lectureId) {
        return lectureId != null ? reservation.lecture.id.eq(lectureId) : null;
    }
}
