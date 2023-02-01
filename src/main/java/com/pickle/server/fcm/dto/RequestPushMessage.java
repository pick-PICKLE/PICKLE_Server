package com.pickle.server.fcm.dto;

import lombok.Getter;

@Getter
public enum RequestPushMessage {

    ReadyPickup("주문하신 상품의 픽업 준비가 완료되었습니다.", "해당 매장으로 픽업 예약 시간에 맞게 방문해주세요"),
    CompletePickup("픽업을 완료했습니다", "픽업하지 않았다면 고객센터로 문의해주세요");
    private final String title;
    private final String body;

    private RequestPushMessage(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
