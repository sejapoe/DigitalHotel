package ru.sejapoe.digitalhotel.data.service.notification;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;
import ru.sejapoe.digitalhotel.data.repository.RoomRepository;

@AndroidEntryPoint
public class NotificationService extends FirebaseMessagingService {
    @Inject
    public LoginRepository loginRepository;

    @Inject
    public RoomRepository roomRepository;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        loginRepository.subscribe(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Map<String, String> data = message.getData();
        String action = data.getOrDefault("action", "");
        if (Objects.requireNonNull(action).equals("close_room")) {
            int roomId = Integer.parseInt(Objects.requireNonNull(data.get("room_id")));
            roomRepository.close(roomId);
        }

    }
}
