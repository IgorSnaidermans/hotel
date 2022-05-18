package com.igors.hotel.reservation.mapping;

import com.igors.hotel.mapping.CommonMapper;
import com.igors.hotel.reservation.Reservation;
import com.igors.hotel.reservation.ReservationModel;
import com.igors.hotel.room.Room;
import com.igors.hotel.room.RoomRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface ReservationMapper extends CommonMapper<Reservation, ReservationModel> {

    @Mapping(target = "roomId", source = "room.id")
    ReservationModel entityToModel(Reservation entity);

    @Mapping(target = "endDate", source = "endDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "startDate", source = "startDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "room", ignore = true)
    Reservation modelToEntity(ReservationModel model);

}
