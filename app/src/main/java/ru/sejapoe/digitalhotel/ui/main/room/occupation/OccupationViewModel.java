package ru.sejapoe.digitalhotel.ui.main.room.occupation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.repository.RoomRepository;

@HiltViewModel
public class OccupationViewModel extends ViewModel {
    private final RoomRepository roomRepository;
    private int roomId;

    @Inject
    public OccupationViewModel(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LiveData<Boolean> isOpen() {
        return roomRepository.isOpened(roomId);
    }

    public void open() {
        roomRepository.open(roomId);
    }
}
