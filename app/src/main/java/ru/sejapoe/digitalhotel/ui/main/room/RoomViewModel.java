package ru.sejapoe.digitalhotel.ui.main.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collection;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.hotel.room.RoomAccess;
import ru.sejapoe.digitalhotel.data.repository.RoomRepository;

@HiltViewModel
public class RoomViewModel extends ViewModel {
    private final RoomRepository roomRepository;

    private int selected = 0;


    @Inject
    public RoomViewModel(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public LiveData<Collection<RoomAccess>> getAccesses() {
        return roomRepository.getAccesses();
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
