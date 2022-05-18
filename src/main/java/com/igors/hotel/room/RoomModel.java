package com.igors.hotel.room;

import com.igors.hotel.reservation.Reservation;
import com.igors.hotel.reservation.ReservationModel;
import com.igors.hotel.reservation.mapping.ReservationMapper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomModel {

    private Long id;

    private String name;

    private List<ReservationModel> reservations = new ArrayList<>();

}
