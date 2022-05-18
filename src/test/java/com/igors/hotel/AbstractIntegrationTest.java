package com.igors.hotel;

import com.igors.hotel.reservation.Reservation;
import com.igors.hotel.reservation.ReservationRepository;
import com.igors.hotel.room.Room;
import com.igors.hotel.room.RoomModel;
import com.igors.hotel.room.RoomRepository;
import com.igors.hotel.room.mapping.RoomMapper;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AbstractIntegrationTest {

    private Long roomId;

    private Long reservationId;

    private RoomModel roomModel;

    @BeforeEach
    public void setUp(@Autowired RoomRepository roomRepository, @Autowired ReservationRepository reservationRepository) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.parse("2020-04-01"));
        reservation.setEndDate(LocalDate.parse("2020-06-01"));
        Room room = new Room();
        room.setName("init");
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        reservation.setRoom(room);
        room.setReservations(reservations);
        Room savedRoom = roomRepository.save(room);
        reservationId = room.getReservations().get(0).getId();
        roomId = savedRoom.getId();
        roomModel = Mappers.getMapper(RoomMapper.class).entityToModel(savedRoom);
    }

    @AfterEach
    public void teardown(@Autowired RoomRepository roomRepository) {
        roomRepository.deleteAll();
    }

    @NotNull
    protected Long getRoomId() {
        return roomId;
    }

    @NotNull
    protected RoomModel getRoomModel() {
        return roomModel;
    }

    @NotNull
    public Long getReservationId() {
        return reservationId;
    }

}
