package com.igors.hotel.reservation;

import com.igors.hotel.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ReservationService victim;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void shouldGetRoomReservations () {
        List<Reservation> result = victim.getRoomReservations(getRoomId());
        assertEquals(1, result.size());
        assertEquals(getReservationId(), result.get(0).getId());
    }

    @Test
    public void shouldCreateReservation() {
        ReservationModel model = new ReservationModel();
        model.setRoomId(getRoomId());
        model.setStartDate("2023-01-01");
        model.setEndDate("2023-03-01");
        Reservation reservation = victim.createReservation(model);
        assertNotNull(reservation.getId());
    }

}