package com.igors.hotel.reservation;

import lombok.Data;

@Data
public class ReservationModel {

    private Long id;

    private Long roomId;

    private String startDate;

    private String endDate;

}
