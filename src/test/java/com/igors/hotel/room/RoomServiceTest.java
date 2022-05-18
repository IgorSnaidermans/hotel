package com.igors.hotel.room;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService victim;

    @Test
    public void shouldThrowExceptionUpdatingNotExistingRoom() {
        when(roomRepository.existsById(Mockito.any())).thenReturn(false);
        RoomModel roomModel = new RoomModel();
        roomModel.setId(1L);
        assertThrows("Unable to update not existing entity", IllegalStateException.class,
                () -> victim.updateRoom(roomModel));
    }
}