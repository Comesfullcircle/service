package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final SseConnectionPool sseConnectionPool;

    /**
     * 주문
     * 주문 내역 찾기
     * 스토어 찾기
     * 연결된 세션 찾아서
     * push
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage){
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(()-> new RuntimeException("사용자 주문내역 없음"));

        // user order entity

        // user order menu

        // user order menu -> store menu

        //response

        //push

        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        //주문 메뉴, 가격, 상태

        //사용자에게 push
        //userConnection.sendMessage();
    }
}
