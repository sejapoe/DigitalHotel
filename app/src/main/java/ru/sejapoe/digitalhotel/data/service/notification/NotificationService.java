package ru.sejapoe.digitalhotel.data.service.notification;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;

@AndroidEntryPoint
public class NotificationService extends FirebaseMessagingService {
    @Inject
    public LoginRepository repository;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        repository.subscribe(token);
    }
}
