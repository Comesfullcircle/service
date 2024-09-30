package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.userorder.UserOrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository repository;

}
