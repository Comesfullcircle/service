package org.delivery.db.userorder.enums;

import lombok.AllArgsConstructor;

public enum UserOrderStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지")
    ;

    UserOrderStatus(String description) {
        this.description = description;
    }

    private String description;
}
