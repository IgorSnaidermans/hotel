package com.igors.hotel.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.igors.hotel.reservation.Reservation;
import com.igors.hotel.reservation.ReservationModel;
import com.igors.hotel.reservation.ReservationService;
import com.igors.hotel.room.Room;
import com.igors.hotel.room.RoomModel;
import com.igors.hotel.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    public Room updateRoom(RoomModel model) {
        return roomService.updateRoom(model);
    }

    public boolean removeRoom(Long id) {
        roomService.remove(id);
        return true;
    }

    public Reservation makeReservation(ReservationModel model) {
        return reservationService.createReservation(model);
    }

}
