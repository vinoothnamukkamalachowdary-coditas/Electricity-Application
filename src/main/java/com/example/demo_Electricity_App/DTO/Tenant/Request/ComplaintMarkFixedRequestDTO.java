package com.example.demo_Electricity_App.DTO.Tenant.Request;

import lombok.Data;

@Data
public class ComplaintMarkFixedRequestDTO {
    /** Optional technician remark describing what was fixed. */
    private String remark;
}
