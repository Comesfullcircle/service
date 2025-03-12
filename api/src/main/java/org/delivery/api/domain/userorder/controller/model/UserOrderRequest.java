package org.delivery.api.domain.userorder.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserOrderRequest {

    @NotNull
    private Long storeId;

    // 주문
    // 특정 사용자가 , 특정 메뉴를 주문
    // 특정 사용자 = 로그인된 세션에 들어있는 사용자
    // 특정 메뉴 id
    @NotNull
    private List<Long> storeMenuIdList;

    public @NotNull Long getStoreId() {
        return storeId;
    }

    public void setStoreId(@NotNull Long storeId) {
        this.storeId = storeId;
    }

    public @NotNull List<Long> getStoreMenuIdList() {
        return storeMenuIdList;
    }

    public void setStoreMenuIdList(@NotNull List<Long> storeMenuIdList) {
        this.storeMenuIdList = storeMenuIdList;
    }
}
