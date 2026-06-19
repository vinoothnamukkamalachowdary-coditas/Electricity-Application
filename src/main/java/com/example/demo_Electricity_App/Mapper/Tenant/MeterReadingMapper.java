package com.example.demo_Electricity_App.Mapper.Tenant;

import com.example.demo_Electricity_App.DTO.Tenant.Response.MeterReadingResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.MeterReading;
import org.springframework.stereotype.Component;

@Component
public class MeterReadingMapper {
    public MeterReadingResponseDTO toResponse(MeterReading reading) {
        MeterReadingResponseDTO dto = new MeterReadingResponseDTO();
        dto.setId(reading.getId());
        dto.setPreviousReading(reading.getPreviousReading());
        dto.setCurrentReading(reading.getCurrentReading());
        dto.setConsumedUnits(reading.getConsumedUnits());
        dto.setRateSnapshotPerUnit(reading.getRateSnapshotPerUnit());
        if (reading.getInstallation() != null) {
            dto.setInstallationId(reading.getInstallation().getId());
            dto.setMeterNumber(reading.getInstallation().getMeterNumber());
        }
        dto.setReadingDate(reading.getReadingDate());
        dto.setMasterBillerId(reading.getMasterBillerId());
        return dto;
    }
}
