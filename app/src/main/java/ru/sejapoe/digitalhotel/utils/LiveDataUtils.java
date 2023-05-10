package ru.sejapoe.digitalhotel.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataUtils {
    public static <T> LiveData<Response<T>> callToResponseLiveData(@NonNull Call<T> call) {
        MutableLiveData<Response<T>> liveData = new MutableLiveData<>();
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                liveData.postValue(response);
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                t.printStackTrace();
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public static LiveData<Boolean> callToSuccessLiveData(@NonNull Call<?> call) {
        return Transformations.map(callToResponseLiveData(call), response -> response != null && response.isSuccessful());
    }

    public static <T> LiveData<T> callToBodyLiveData(@NonNull Call<T> call) {
        return Transformations.map(callToResponseLiveData(call), Response::body);
    }

    public static <T> LiveData<T> callToBodyLiveData(@NonNull Call<T> call, @NonNull T defaultValue) {
        return Transformations.map(callToBodyLiveData(call), t -> t == null ? defaultValue : t);
    }

    public static <T> LiveData<List<T>> callToListLiveData(@NonNull Call<List<T>> call) {
        return callToBodyLiveData(call, Collections.emptyList());
    }

    public static LiveData<Integer> callToStatusLiveData(@NonNull Call<?> call) {
        return Transformations.map(callToResponseLiveData(call), response -> response == null ? -1 : response.code());
    }

    @NonNull
    public static <R, A, B> LiveData<R> combine(@NonNull LiveData<A> aLiveData, @NonNull LiveData<B> bLiveData, Combinator<R, A, B> combinator) {
        MutableLiveData<R> rMutableLiveData = new MutableLiveData<>();
        AtomicReference<A> a = new AtomicReference<>();
        AtomicReference<B> b = new AtomicReference<>();
        observeOnce(aLiveData, newValue -> {
            a.set(newValue);
            if (b.get() != null) rMutableLiveData.postValue(combinator.combine(a.get(), b.get()));
        });
        observeOnce(bLiveData, newValue -> {
            b.set(newValue);
            if (a.get() != null) rMutableLiveData.postValue(combinator.combine(a.get(), b.get()));
        });
        return rMutableLiveData;
    }

    public static <T> void observeOnce(@NonNull LiveData<T> liveData, Observer<T> observer) {
        liveData.observeForever(t -> {
            observer.onChanged(t);
            liveData.removeObserver(observer);
        });
    }

    public interface Combinator<R, A, B> {
        R combine(A a, B b);
    }
}
