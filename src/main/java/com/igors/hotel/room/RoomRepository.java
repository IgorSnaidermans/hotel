package com.igors.hotel.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT count(res.id) FROM Reservation res " +
            "WHERE (:from NOT BETWEEN res.startDate AND res.endDate) " +
            "AND (:to NOT BETWEEN res.startDate AND res.endDate)")
    int getNotInDateRange(@Param("from") LocalDate from, @Param("to")  LocalDate to);
}
