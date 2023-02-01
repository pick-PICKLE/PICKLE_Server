package com.pickle.server.fcm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import com.pickle.server.fcm.dto.RequestPushMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class FcmService {
    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;
    @Value("${fcm.key.scope}")
    private String fireBaseScope;

    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                                    .createScoped(List.of(fireBaseScope))).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase initialization complete");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

//    public String sendMsg(RequestPushMessage msg) throws FirebaseMessagingException {
//        Message message = getMessage(msg);
//    }
//
//    private Message getMessage(RequestPushMessage msg) {
//        Message message = Message.builder().setNotification();
//    }

}
