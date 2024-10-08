package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final SseConnectionPool sseConnectionPool;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    /**
     * 주문
     * 주문 내역 찾기
     * 스토어 찾기
     * 연결된 세션 찾아서
     * push
     */
   /* public void pushUserOrder(UserOrderMessage userOrderMessage){
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(()-> new RuntimeException("사용자 주문내역 없음"));

        // user order menu
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        // user order menu -> store menu
        var storeMenuResponseList = userOrderMenuList.stream()
                .map(userOrderMenuEntity ->{
                    return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                })
                .map(storeMenuEntity ->{
                    return storeMenuConverter.toResponse(storeMenuEntity);
                })
                .collect(Collectors.toList());

        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        // response
        var push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build()
                ;

       // var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // 사용자에게 push
       // userConnection.sendMessage(push);


        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        if (userConnection != null) {
            userConnection.sendMessage(push);
        } else {
            log.warn("스토어 ID {}에 대한 연결된 세션이 없습니다.", userOrderEntity.getStoreId());
        }


    }*/

    public void pushUserOrder(UserOrderMessage userOrderMessage){
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElse(null); // null로 반환하여 예외를 던지지 않음

        if (userOrderEntity == null) {
            log.error("주문 내역을 찾을 수 없습니다. 주문 ID: {}", userOrderMessage.getUserOrderId());
            return;  // 이후 로직을 수행하지 않고 종료
        }

        // user order menu
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        // user order menu -> store menu
        var storeMenuResponseList = userOrderMenuList.stream()
                .map(userOrderMenuEntity -> {
                    return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                })
                .map(storeMenuEntity -> {
                    return storeMenuConverter.toResponse(storeMenuEntity);
                })
                .collect(Collectors.toList());

        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        // response 생성
        var push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build();

        // 세션 가져오기
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        if (userConnection != null) {
            // 세션이 있을 경우 사용자에게 메시지 전송
            userConnection.sendMessage(push);
        } else {
            // 세션이 없을 경우 경고 로그 남기기
            log.warn("스토어 ID {}에 대한 연결된 세션이 없습니다.", userOrderEntity.getStoreId());
            // 세션이 없을 경우의 추가 처리 로직(필요 시)
            // 예를 들어: 다시 세션을 시도하거나 다른 알림 방식 사용
        }
    }

}