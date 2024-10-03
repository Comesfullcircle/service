package org.delivery.storeadmin.domain.user.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreUserRegisterRequest {

    @JsonProperty("storeName")
    @NotBlank
    private String storeName;

    @JsonProperty("email")
    @NotBlank
    private String email;

    @JsonProperty("password")
    @NotBlank
    private String password;

    @JsonProperty("role")
    @NotBlank
    private StoreUserRole role;
}
