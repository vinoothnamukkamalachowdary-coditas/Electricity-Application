package com.example.demo_Electricity_App.DTO.Tenant.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BpoAssignmentRequestDTO {
    @NotNull(message = "Tenant user ID must not be null")
    private Long tenantUserId;

    @NotNull(message = "BPO state ID must not be null")
    private Long bpoStateId;

    /** Cross-schema reference -> master.district.id */
    @NotNull(message = "Master district ID must not be null")
    private Long masterDistrictId;

    /** Cross-schema reference -> master.city.id */
    @NotNull(message = "Master city ID must not be null")
    private Long masterCityId;
}
