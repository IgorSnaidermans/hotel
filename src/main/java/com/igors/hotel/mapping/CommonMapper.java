package com.igors.hotel.mapping;

import com.igors.hotel.reservation.Reservation;
import com.igors.hotel.reservation.ReservationModel;
import org.mapstruct.Mapping;

public interface CommonMapper<E, M> {

    M entityToModel(E entity);

    E modelToEntity(M model);
}
