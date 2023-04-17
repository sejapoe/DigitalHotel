package ru.sejapoe.digitalhotel.ui.loading;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.login.Session;
import ru.sejapoe.digitalhotel.data.repository.SessionRepository;

@HiltViewModel
public class LoadingViewModel extends ViewModel {
    private final SessionRepository sessionRepository;

    @Inject
    public LoadingViewModel(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public LiveData<Session> getSession() {
        return sessionRepository.getSession();
    }
}
