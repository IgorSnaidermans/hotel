package com.igors.hotel.room;

import com.igors.hotel.room.mapping.RoomMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room updateRoom(RoomModel model) {
        if (model.getId() != null && !roomRepository.existsById(model.getId())) {
            throw new IllegalStateException("Unable to update not existing entity");
        }
        Room room = Mappers.getMapper(RoomMapper.class).modelToEntity(model);
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void remove(Long id) {
        roomRepository.deleteById(id);
    }

    public int getAvailableRoomsStats(LocalDate from, LocalDate to) {
        return roomRepository.getNotInDateRange(from, to);
    }

}
