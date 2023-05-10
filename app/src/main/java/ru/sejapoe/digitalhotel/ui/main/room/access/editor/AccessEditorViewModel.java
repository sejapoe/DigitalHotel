package ru.sejapoe.digitalhotel.ui.main.room.access.editor;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.hotel.access.AccessShare;
import ru.sejapoe.digitalhotel.data.model.hotel.access.AccessShareEdit;
import ru.sejapoe.digitalhotel.data.model.hotel.access.RightsComposition;
import ru.sejapoe.digitalhotel.data.repository.AccessRepository;

@HiltViewModel
public class AccessEditorViewModel extends ViewModel {
    private final AccessRepository accessRepository;

    private int accessId;

    @Inject
    public AccessEditorViewModel(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    public LiveData<AccessShare> getShare() {
        return accessRepository.getShare(accessId);
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }

    public LiveData<Boolean> save(@NonNull RightsComposition rightsComposition, int limit) {
        return accessRepository.editShare(accessId, new AccessShareEdit(rightsComposition.getValue(), limit));
    }
}
