package org.delivery.api.domain.userorder.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderRequest {

    @JsonProperty("storeId")
    @NotNull
    private Long storeId;

    // 주문
    // 특정 사용자가, 특정 메뉴를 주문
    // 특정 사용자 = 로그인된 세션에 들어있는 사용자
    // 특정 메뉴 id

    @JsonProperty("storeMenuIdList")
    @NotNull
    private List<Long> storeMenuIdList;

}
