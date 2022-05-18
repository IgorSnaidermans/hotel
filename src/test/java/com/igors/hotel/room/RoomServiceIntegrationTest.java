package com.igors.hotel.room;

import com.igors.hotel.AbstractIntegrationTest;
import com.igors.hotel.reservation.Reservation;
import com.igors.hotel.reservation.ReservationModel;
import com.igors.hotel.reservation.ReservationRepository;
import com.igors.hotel.room.mapping.RoomMapper;
import graphql.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private RoomService victim;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void shouldGetAllRooms() {
        Assert.assertNotEmpty(victim.getAllRooms(), "Rooms should exist");
    }

    @Test
    public void shouldSaveRoom() {
        RoomModel roomModel = new RoomModel();
        roomModel.setName("name");
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setStartDate("2020-01-01");
        reservationModel.setEndDate("2020-02-01");
        roomModel.setReservations(Collections.singletonList(reservationModel));
        Room room = victim.updateRoom(roomModel);
        Assert.assertTrue(roomRepository.findById(room.getId()).isPresent(), "Room should exist");
    }

    @Test
    public void shouldUpdateRoom() {
        RoomModel model = getRoomModel();
        model.getReservations().get(0).setEndDate("2030-01-01");
        model.setName("updated");
        victim.updateRoom(model);
        assertEquals(victim.getAllRooms().size(), 1);
        Room result = roomRepository.getById(getRoomId());
        assertEquals("updated", result.getName());
        assertEquals(1, result.getReservations().size());
        assertTrue(result.getReservations().get(0).getEndDate().isEqual(LocalDate.parse("2030-01-01")));
    }

    @Test
    public void shouldRemoveRoom() {
        victim.remove(getRoomId());
        assertTrue(roomRepository.findById(getRoomId()).isEmpty());
        assertTrue(reservationRepository.findById(getReservationId()).isEmpty());
    }

    @Test
    public void shouldGetAvailableRoomCount() {
        assertEquals(1, victim.getAvailableRoomsStats(LocalDate.parse("2020-01-01"), LocalDate.parse("2020-02-01")));
        assertEquals(0, victim.getAvailableRoomsStats(LocalDate.parse("2020-01-01"), LocalDate.parse("2020-05-01")));
    }

}