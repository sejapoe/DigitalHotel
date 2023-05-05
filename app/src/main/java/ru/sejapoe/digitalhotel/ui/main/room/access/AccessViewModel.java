package ru.sejapoe.digitalhotel.ui.main.room.access;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.repository.AccessRepository;

@HiltViewModel
public class AccessViewModel extends ViewModel {
    private final AccessRepository accessRepository;

    @Inject
    public AccessViewModel(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }
}
