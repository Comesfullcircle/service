package org.delivery.storeadmin.domain.storeuser.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreUserRegisterRequest {

    @JsonProperty("storeName")
    @NotBlank(message = "매장 이름(storeName)은 필수 입력 값입니다.")
    private String storeName;

    @JsonProperty("email")
    @NotBlank(message = "이메일(email)은 필수 입력 값입니다.")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "비밀번호(password)는 필수 입력 값입니다.")
    private String password;

    @JsonProperty("role")
    @NotNull(message = "역할(role)은 필수 입력 값입니다.")
    private StoreUserRole role;
}
