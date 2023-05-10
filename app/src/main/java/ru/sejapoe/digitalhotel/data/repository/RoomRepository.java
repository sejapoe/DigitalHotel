package ru.sejapoe.digitalhotel.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.sejapoe.digitalhotel.data.model.hotel.room.RoomAccess;
import ru.sejapoe.digitalhotel.data.source.network.service.RoomService;
import ru.sejapoe.digitalhotel.utils.LiveDataUtils;

@Singleton
public class RoomRepository {
    private final RoomService roomService;
    private final Map<Integer, RoomAccess> accessMap = new HashMap<>();

    private final MutableLiveData<Collection<RoomAccess>> accesses = new MutableLiveData<>();

    @Inject
    public RoomRepository(RoomService roomService) {
        this.roomService = roomService;
    }

    public LiveData<Boolean> isOpened(int id) {
        return Transformations.map(this.accesses, accesses ->
                accesses.stream().filter(access -> access.getRoom().getId() == id).findFirst().map(access -> access.getRoom().isOpen()).orElse(false)
        );
    }

    public void open(int id) {
        LiveDataUtils.observeOnce(LiveDataUtils.callToSuccessLiveData(roomService.open(id)), isSuccess -> {
            if (isSuccess) {
                setOpened(id, true);
            }
        });
    }

    public void setOpened(int id, boolean open) {
        accessMap.get(id).getRoom().setOpen(open);
        accesses.postValue(accessMap.values());
    }

    public LiveData<Collection<RoomAccess>> getAccesses() {
        loadAccesses();
        return accesses;
    }

    private void loadAccesses() {
        LiveData<List<RoomAccess>> data = LiveDataUtils.callToListLiveData(roomService.getAccesses());
        LiveDataUtils.observeOnce(data, this::setAccesses);
    }

    private void setAccesses(List<RoomAccess> accesses) {
        accessMap.clear();
        accesses.forEach(access -> {
            accessMap.put(access.getRoom().getId(), access);
        });
        this.accesses.postValue(accessMap.values());
    }
}
