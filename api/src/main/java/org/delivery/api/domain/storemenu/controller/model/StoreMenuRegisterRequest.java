package org.delivery.api.domain.storemenu.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenuRegisterRequest {

    @NotNull
    @JsonProperty("storeId")
    private Long storeId;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("amount")
    private BigDecimal amount;

    @NotBlank
    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;

}
