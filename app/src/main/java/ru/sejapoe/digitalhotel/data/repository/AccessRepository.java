package ru.sejapoe.digitalhotel.data.repository;

import androidx.lifecycle.LiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.sejapoe.digitalhotel.data.model.hotel.AccessShareEdit;
import ru.sejapoe.digitalhotel.data.source.network.service.AccessService;
import ru.sejapoe.digitalhotel.utils.LiveDataUtils;

@Singleton
public class AccessRepository {
    private final AccessService accessService;

    @Inject
    public AccessRepository(AccessService accessService) {
        this.accessService = accessService;
    }

    public LiveData<Boolean> shareAccess(int roomId, String username) {
        return LiveDataUtils.callToSuccessLiveData(accessService.shareAccess(roomId, username));
    }

    public LiveData<Boolean> editShare(int roomId, int shareId, AccessShareEdit accessShareEdit) {
        return LiveDataUtils.callToSuccessLiveData(accessService.editShare(roomId, shareId, accessShareEdit));
    }

    public LiveData<Boolean> removeShare(int roomId, int shareId) {
        return LiveDataUtils.callToSuccessLiveData(accessService.deleteShare(roomId, shareId));
    }
}
