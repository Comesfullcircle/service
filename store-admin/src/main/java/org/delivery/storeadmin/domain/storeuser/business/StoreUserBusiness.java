package org.delivery.storeadmin.domain.storeuser.business;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.storeuser.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.storeuser.service.StoreUserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreUserBusiness {
    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    public StoreUserResponse register(StoreUserRegisterRequest request) {

        System.out.println("DEBUG: storeName - " + request.getStoreName()); // 디버깅 로그 추가
        System.out.println("DEBUG: 요청 객체 - " + request); // 전체 객체 출력

        // 1️⃣ storeName이 비어 있는지 확인
        if (request.getStoreName() == null || request.getStoreName().trim().isEmpty()) {
            System.out.println("DEBUG: storeName 값이 비어 있음!");
            throw new IllegalArgumentException("매장 이름(storeName)이 비어있습니다.");
        }

        // 2️⃣ storeName과 status로 StoreEntity 조회 (없으면 예외 발생)
        var storeEntity = Optional.ofNullable(storeRepository.findFirstByNameAndStatusOrderByIdDesc(
                        request.getStoreName(), StoreStatus.REGISTERED))
                .orElseThrow(() -> new NoSuchElementException("해당하는 매장이 없습니다: " + request.getStoreName()));

        // 3️⃣ 유저 등록 로직 진행
        var entity = storeUserConverter.toEntity(request, storeEntity);
        var newEntity = storeUserService.register(entity);

        // 4️⃣ 변환 후 반환
        return storeUserConverter.toResponse(newEntity, storeEntity);
    }
}
