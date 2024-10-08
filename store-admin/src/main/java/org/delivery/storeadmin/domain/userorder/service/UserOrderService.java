package org.delivery.storeadmin.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    private static final Logger log = LoggerFactory.getLogger(UserOrderService.class);

    public Optional<UserOrderEntity> getUserOrder(Long id) {
        return Optional.ofNullable(userOrderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("주문 내역을 찾을 수 없습니다. 주문 ID: " + id);
                    return new RuntimeException("주문 내역을 찾을 수 없습니다. 주문 ID: " + id);
                }));
    }
}
