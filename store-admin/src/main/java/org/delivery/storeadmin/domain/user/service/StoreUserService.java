package org.delivery.storeadmin.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.db.storeuser.StoreUserRepository;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreUserService {

    private static final Logger logger = LoggerFactory.getLogger(StoreUserService.class);

    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder;

    public StoreUserEntity register(
            StoreUserEntity storeUserEntity
    ){
        logger.info("Registering new user with email: {}", storeUserEntity.getEmail());
        storeUserEntity.setStatus(StoreUserStatus.REGISTERED);

        // Log password before and after encoding (for debug purposes only; be careful with sensitive data)
        logger.debug("Password before encoding: {}", storeUserEntity.getPassword());
        storeUserEntity.setPassword(passwordEncoder.encode(storeUserEntity.getPassword()));
        logger.debug("Password after encoding: {}", storeUserEntity.getPassword());

        storeUserEntity.setRegisteredAt(LocalDateTime.now());


        StoreUserEntity savedUser = storeUserRepository.save(storeUserEntity);
        logger.info("User registered successfully with ID: {}", savedUser.getId());

        return savedUser;
    }

    public Optional<StoreUserEntity> getRegisterUser(String email){
        logger.info("Fetching registered user with email: {}", email);

        Optional<StoreUserEntity> user = storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email, StoreUserStatus.REGISTERED);

        if (user.isPresent()) {
            logger.info("Registered user found with email: {}", email);
        } else {
            logger.warn("No registered user found with email: {}", email);
        }

        return user;
    }
}