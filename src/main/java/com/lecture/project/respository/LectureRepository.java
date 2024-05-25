package com.lecture.project.respository;

import com.lecture.project.model.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {


    @CachePut(cacheNames = "lecture", key = "#result.id")
    @Override
    <S extends Lecture> S save(S entity);

    List<Lecture> findAllByLectureDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT l From Lecture l WHERE l.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Lecture> findByIdLock(long id);

    @Transactional
    @Modifying
    @Query("UPDATE Lecture l SET l.participantNumber = :number WHERE l.id = :id")
    void updateLectureParticipant(long id, int number);
}
