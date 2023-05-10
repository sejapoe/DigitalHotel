package ru.sejapoe.digitalhotel.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.sejapoe.digitalhotel.data.model.hotel.access.AccessShare;
import ru.sejapoe.digitalhotel.data.model.hotel.access.AccessShareEdit;
import ru.sejapoe.digitalhotel.data.source.network.service.AccessService;
import ru.sejapoe.digitalhotel.utils.LiveDataUtils;

@Singleton
public class AccessRepository {
    private final AccessService accessService;

    @Inject
    public AccessRepository(AccessService accessService) {
        this.accessService = accessService;
    }

    public LiveData<List<AccessShare>> getSharedAccesses(int roomId) {
        return LiveDataUtils.callToListLiveData(accessService.getSharedAccesses(roomId));
    }

    public LiveData<Boolean> shareAccess(int roomId, List<String> usernames) {
        return LiveDataUtils.callToSuccessLiveData(accessService.shareAccess(roomId, usernames));
    }

    public LiveData<AccessShare> getShare(int shareId) {
        return LiveDataUtils.callToBodyLiveData(accessService.getShare(shareId));
    }

    public LiveData<Boolean> editShare(int shareId, AccessShareEdit accessShareEdit) {
        return LiveDataUtils.callToSuccessLiveData(accessService.editShare(shareId, accessShareEdit));
    }

    public LiveData<Boolean> removeShare(int shareId) {
        return LiveDataUtils.callToSuccessLiveData(accessService.deleteShare(shareId));
    }
}
