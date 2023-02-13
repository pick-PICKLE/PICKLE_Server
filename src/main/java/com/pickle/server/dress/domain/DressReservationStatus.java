package com.pickle.server.dress.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DressReservationStatus {
    ORDER_COMPLETION(Constants.orderCompletion),
    PICKUP_WAITING(Constants.pickupWaiting),
    PICKUP_COMPLETION(Constants.pickupCompletion),
    PURCHASE_CONFIRMATION(Constants.purchaseCompletion),
    CANCELED_ORDER(Constants.purchaseCompletion);

    private final String sortBy;

    public static boolean existsDressReservationStatusByName(String name) {
        for (DressReservationStatus drs : values()) {
            if (drs.sortBy.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static class Constants {
        public static final String orderCompletion = "주문완료";
        public static final String pickupWaiting = "픽업대기";
        public static final String pickupCompletion = "픽업완료";
        public static final String purchaseCompletion = "구매확정";
        public static final String canceledOrder = "주문취소";
    }
}
