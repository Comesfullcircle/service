package org.delivery.db.storeuser;

import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreUserRepository extends JpaRepository<StoreUserEntity, Long> {

    // select * from store_user where email = ? and status = ? order by id desc limit 1
    //Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email, StoreUserStatus status);

    @Query("SELECT su FROM StoreUserEntity su JOIN FETCH su.store WHERE su.email = :email AND su.status = :status ORDER BY su.id DESC")
    Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(@Param("email") String email, @Param("status") StoreUserStatus status);

}
