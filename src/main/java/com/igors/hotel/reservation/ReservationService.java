package com.igors.hotel.reservation;

import com.igors.hotel.reservation.mapping.ReservationMapper;
import com.igors.hotel.room.Room;
import com.igors.hotel.room.RoomRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReservationService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getRoomReservations(Long roomId) {
        return reservationRepository.findAllByRoomId(roomId);
    }

    public Reservation createReservation(ReservationModel model) {
        validate(model);
        if (!checkAvailability(model)) {
            throw new IllegalStateException("Already busy days");
        }
        Reservation reservation = Mappers.getMapper(ReservationMapper.class).modelToEntity(model);
        Room room = roomRepository.findById(model.getRoomId())
                .orElseThrow(() -> new IllegalStateException("Room doesn't exist"));
        reservation.setRoom(room);
        return reservationRepository.save(reservation);
    }

    public boolean checkAvailability(ReservationModel model) {
        validate(model);
        boolean isAvailable = true;
        List<Reservation> reservations = reservationRepository.findAllByRoomId(model.getRoomId());
        if (!reservations.isEmpty()) {
            LocalDate parsedFrom = LocalDate.parse(model.getStartDate());
            LocalDate parsedTo = LocalDate.parse(model.getEndDate());
            isAvailable = scanIfBusy(parsedFrom, parsedTo, reservations);
        }

        return isAvailable;
    }

    private boolean scanIfBusy(LocalDate from, LocalDate to, List<Reservation> reservations) {
        List<LocalDate> requestedDates = from.datesUntil(to).collect(Collectors.toList());
        return reservations.stream().anyMatch(reservation ->
        {
            if (isRequestedOnLastOrFirstReservedDay(from, to, reservation)) {
                return false;
            }
            Stream<LocalDate> reservedDays = reservation.getStartDate().datesUntil(reservation.getEndDate());
            return reservedDays.noneMatch(requestedDates::contains);
        });
    }

    private boolean isRequestedOnLastOrFirstReservedDay(LocalDate from, LocalDate to, Reservation reservation) {
        return from.isEqual(reservation.getEndDate()) || to.isEqual(reservation.getStartDate());
    }

    private void validate(ReservationModel model) {
        LocalDate from = LocalDate.parse(model.getStartDate());
        LocalDate to = LocalDate.parse(model.getEndDate());
        Assert.isTrue(from.isBefore(to), "Reservation end date should be before start date");
    }
}
