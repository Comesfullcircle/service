package org.delivery.storeadmin.domain.storeuser.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserResponse;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class StoreUserConverter {

    private final StoreRepository storeRepository; // ✅ 정상적으로 주입되도록 설정 확인

    public StoreUserEntity toEntity(
            StoreUserRegisterRequest request,
            StoreEntity storeEntity
    ){
        return StoreUserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .store(storeEntity) // ✅ storeEntity 직접 참조
                .build();
    }

    public StoreUserResponse toResponse(
            StoreUserEntity storeUserEntity,
            StoreEntity storeEntity
    ){
        return StoreUserResponse.builder()
                .user(
                        StoreUserResponse.UserResponse.builder()
                                .id(storeUserEntity.getId())
                                .email(storeUserEntity.getEmail())
                                .status(storeUserEntity.getStatus())
                                .role(storeUserEntity.getRole())
                                .registeredAt(storeUserEntity.getRegisteredAt())
                                .unregisteredAt(storeUserEntity.getUnregisteredAt())
                                .lastLoginAt(storeUserEntity.getLastLoginAt())
                                .build()
                )
                .store(
                        StoreUserResponse.StoreResponse.builder()
                                .id(storeEntity.getId())
                                .name(storeEntity.getName())
                                .build()
                )
                .build();
    }

    public StoreUserResponse toResponse(UserSession userSession){
        if (userSession.getStoreId() == null) {
            throw new IllegalArgumentException("UserSession에 storeId가 없습니다.");
        }

        // storeId를 기반으로 StoreEntity 조회
        StoreEntity storeEntity = storeRepository.findById(userSession.getStoreId())
                .orElseThrow(() -> new NoSuchElementException("StoreEntity를 찾을 수 없습니다. ID: " + userSession.getStoreId()));

        return StoreUserResponse.builder()
                .user(
                        StoreUserResponse.UserResponse.builder()
                                .id(userSession.getUserId())
                                .email(userSession.getEmail())
                                .status(userSession.getStatus())
                                .role(userSession.getRole())
                                .registeredAt(userSession.getRegisteredAt())
                                .unregisteredAt(userSession.getUnregisteredAt())
                                .lastLoginAt(userSession.getLastLoginAt())
                                .build()
                )
                .store(
                        StoreUserResponse.StoreResponse.builder()
                                .id(storeEntity.getId())  // ✅ storeEntity에서 ID 가져오기
                                .name(storeEntity.getName())  // ✅ storeEntity에서 Name 가져오기
                                .build()
                )
                .build();
    }
}
