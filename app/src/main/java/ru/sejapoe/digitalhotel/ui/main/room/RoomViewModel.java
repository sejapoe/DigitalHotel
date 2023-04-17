package ru.sejapoe.digitalhotel.ui.main.room;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sejapoe.digitalhotel.data.source.network.service.RoomService;

@HiltViewModel
public class RoomViewModel extends ViewModel {
    private final RoomService testService;

    @Inject
    public RoomViewModel(RoomService testService) {
        this.testService = testService;
    }

    public void test() {
        testService.test().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
