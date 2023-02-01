package com.pickle.server.fcm.controller;

import com.pickle.server.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class FcmController {
    private final FcmService fcmService;

    @PostMapping("")
    public ResponseEntity pushMessage() {

    }
}
