package com.igors.hotel.reservation;

import com.igors.hotel.room.Room;
import com.igors.hotel.room.RoomRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Captor
    private ArgumentCaptor<Reservation> reservationCaptor;

    @InjectMocks
    @Spy
    private ReservationService victim;

    @Test
    public void createReservationShouldCheckAvailability() {
        when(roomRepository.findById(any())).thenReturn(Optional.of(new Room()));
        ReservationModel model = createModel("2020-01-01", "2020-03-01");
        victim.createReservation(model);
        verify(victim, atLeastOnce()).checkAvailability(model);
    }

    @Test
    public void createReservationShouldThrowExceptionIfBusy() {
        mockReservation();
        ReservationModel model = createModel("2020-02-01", "2020-04-10");
        assertThrows("Already busy days", IllegalStateException.class, () -> victim.createReservation(model));
    }

    @Test
    public void shouldCreateReservation() {
        Room room = new Room();
        when(roomRepository.findById(any())).thenReturn(Optional.of(room));
        ReservationModel model = createModel("2020-01-01", "2020-03-01");
        victim.createReservation(model);
        verify(victim, atLeastOnce()).checkAvailability(model);
        verify(reservationRepository).save(reservationCaptor.capture());
        Reservation result = reservationCaptor.getValue();
        assertEquals(result.getRoom(), room);
        assertTrue(result.getStartDate().isEqual(LocalDate.parse("2020-01-01")));
        assertTrue(result.getEndDate().isEqual(LocalDate.parse("2020-03-01")));
    }

    @Test
    public void shouldReturnTrueIfDaysAreAvailable() {
        mockReservation();
        ReservationModel model = createModel("2020-01-01", "2020-03-01");
        assertTrue(victim.checkAvailability(model));
    }

    @Test
    public void shouldReturnFalseIfDaysAreBusy() {
        mockReservation();
        ReservationModel model = createModel("2020-02-01", "2020-04-10");
        assertFalse(victim.checkAvailability(model));
    }

    @Test
    public void shouldReturnFalseIfRequestedLastDays() {
        mockReservation();
        ReservationModel model = createModel("2020-05-01", "2020-06-01");
        assertFalse(victim.checkAvailability(model));
    }

    @Test
    public void shouldReturnFalseIfRequestedFirstDays() {
        mockReservation();
        ReservationModel model = createModel("2020-03-01", "2020-04-01");
        assertFalse(victim.checkAvailability(model));
    }

    @Test
    public void shouldValidateStartDayIsBeforeLastDay() {
        ReservationModel model = createModel("2020-05-01", "2020-03-01");
        assertThrows("Reservation end date should be before start date", IllegalArgumentException.class,
                () -> victim.checkAvailability(model));
    }

    private ReservationModel createModel(String startDate, String endDate) {
        ReservationModel model = new ReservationModel();
        model.setRoomId(0L);
        model.setStartDate(startDate);
        model.setEndDate(endDate);
        return model;
    }

    private void mockReservation() {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.parse("2020-04-01"));
        reservation.setEndDate(LocalDate.parse("2020-05-01"));
        when(reservationRepository.findAllByRoomId(0L)).thenReturn(Collections.singletonList(reservation));
    }
}