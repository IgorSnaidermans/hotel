package com.igors.hotel.room.mapping;

import com.igors.hotel.mapping.CommonMapper;
import com.igors.hotel.reservation.Reservation;
import com.igors.hotel.reservation.ReservationModel;
import com.igors.hotel.room.Room;
import com.igors.hotel.room.RoomModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper extends CommonMapper<Room, RoomModel> {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    Room modelToEntity(RoomModel model);

    RoomModel entityToModel(Room entity);
}
