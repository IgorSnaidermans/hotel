package com.igors.hotel.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.igors.hotel.reservation.Reservation;
import com.igors.hotel.reservation.ReservationModel;
import com.igors.hotel.reservation.ReservationService;
import com.igors.hotel.room.Room;
import com.igors.hotel.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    public List<Room> getRooms() {
        return roomService.getAllRooms();
    }

    public int getAvailableRoomsStats(String from, String to) {
        LocalDate parsedFrom = LocalDate.parse(from);
        LocalDate parsedTo = LocalDate.parse(to);
        return roomService.getAvailableRoomsStats(parsedFrom, parsedTo);
    }

    public List<Reservation> getSchedule(Long roomId) {
        return reservationService.getRoomReservations(roomId);
    }

    public boolean checkAvailability(ReservationModel model) {

        return reservationService.checkAvailability(model);
    }

}
