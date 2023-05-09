package ru.sejapoe.digitalhotel.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sejapoe.digitalhotel.data.model.hotel.room.Occupation;
import ru.sejapoe.digitalhotel.data.source.network.service.RoomService;
import ru.sejapoe.digitalhotel.utils.LiveDataUtils;

@Singleton
public class RoomRepository {
    private final RoomService roomService;
    private final Map<Integer, Occupation> occupationMap = new HashMap<>();

    private final MutableLiveData<Collection<Occupation>> occupations = new MutableLiveData<>();

    @Inject
    public RoomRepository(RoomService roomService) {
        this.roomService = roomService;
    }

    public LiveData<Boolean> isOpened(int id) {
        return Transformations.map(occupations, occupations1 ->
                occupations1.stream().filter(occupation -> occupation.getRoom().getId() == id).findFirst().map(occupation -> occupation.getRoom().isOpen()).orElse(false)
        );
    }

    public void open(int id) {
        roomService.open(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    occupationMap.get(id).getRoom().setOpen(true);
                    occupations.postValue(occupationMap.values());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void close(int id) {
        Log.d("Test", String.valueOf(id));
        occupationMap.get(id).getRoom().setOpen(false);
        occupations.postValue(occupationMap.values());
    }

    public LiveData<Collection<Occupation>> getOccupations() {
        loadOccupations();
        return occupations;
    }

    private void loadOccupations() {
        LiveData<List<Occupation>> data = LiveDataUtils.callToListLiveData(roomService.getOccupations());
        data.observeForever(new Observer<List<Occupation>>() {
            @Override
            public void onChanged(List<Occupation> occupations) {
                setOccupations(occupations);
                data.removeObserver(this);
            }
        });
    }

    private void setOccupations(List<Occupation> occupations) {
        occupationMap.clear();
        occupations.forEach(occupation -> {
            occupationMap.put(occupation.getRoom().getId(), occupation);
        });
        this.occupations.postValue(occupationMap.values());
    }
}
