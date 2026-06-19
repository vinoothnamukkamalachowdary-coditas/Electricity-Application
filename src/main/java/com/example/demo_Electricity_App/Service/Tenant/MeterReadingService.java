package com.example.demo_Electricity_App.Service.Tenant;

import com.example.demo_Electricity_App.DTO.Master.Response.MasterUserLookupResponseDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Request.MeterReadingRequestDTO;
import com.example.demo_Electricity_App.DTO.Tenant.Response.MeterReadingResponseDTO;
import com.example.demo_Electricity_App.Entity.Tenant.MeterInstallation;
import com.example.demo_Electricity_App.Entity.Tenant.MeterReading;
import com.example.demo_Electricity_App.Entity.Tenant.MeterTypes;
import com.example.demo_Electricity_App.Enums.Role;
import com.example.demo_Electricity_App.Exception.InvalidOperationException;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Tenant.MeterReadingMapper;
import com.example.demo_Electricity_App.Repository.Tenant.MeterInstallationRepository;
import com.example.demo_Electricity_App.Repository.Tenant.MeterReadingRepository;
import com.example.demo_Electricity_App.Service.Master.MasterUserLookupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;
    private final MeterInstallationRepository meterInstallationRepository;
    private final MasterUserLookupService masterLookupService;
//    private final BillsService billsService;
    private final MeterReadingMapper mapper;

    public MeterReadingResponseDTO recordReading(@Valid MeterReadingRequestDTO dto) {
        MeterInstallation installation = meterInstallationRepository.findById(dto.getInstallationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Meter installation not found: " + dto.getInstallationId()));

        MeterTypes meterType = installation.getCustomers() != null
                ? installation.getCustomers().getMeterType()
                : null;
        if (meterType == null) {
            throw new InvalidOperationException(
                    "Cannot determine meter type configuration for installation " + dto.getInstallationId());
        }

        validateAntiForgeryPhotos(dto, meterType);

        MasterUserLookupResponseDTO biller =
                masterLookupService.resolveUserWithRole(dto.getMasterBillerId(), Role.BILLER);

        Long masterServiceAreaId = installation.getCustomers().getMasterServiceAreaId();
        if (masterServiceAreaId != null) {
            masterLookupService.assertBillerBelongsToServiceArea(masterServiceAreaId, biller.getId());
        }

        Double previousReading = meterReadingRepository
                .findFirstByInstallationIdOrderByReadingDateDesc(installation.getId())
                .map(MeterReading::getCurrentReading)
                .orElse(0.0);

        if (dto.getCurrentReading() < previousReading) {
            throw new InvalidOperationException(
                    "Current reading (" + dto.getCurrentReading()
                            + ") cannot be lower than the previous reading (" + previousReading + ")");
        }

        MeterReading reading = new MeterReading();
        reading.setPreviousReading(previousReading);
        reading.setCurrentReading(dto.getCurrentReading());
        reading.setConsumedUnits(dto.getCurrentReading() - previousReading);
        reading.setRateSnapshotPerUnit(meterType.getRatePerUnit().doubleValue());
        reading.setInstallation(installation);
        reading.setReadingDate(LocalDateTime.now());
        reading.setMasterBillerId(dto.getMasterBillerId());

        MeterReading saved = meterReadingRepository.save(reading);

//        billsService.generateFromReading(saved, installation.getCustomers());

        return mapper.toResponse(saved);
    }

    public MeterReadingResponseDTO getById(Long id) {
        return mapper.toResponse(meterReadingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meter reading not found: " + id)));
    }

    public List<MeterReadingResponseDTO> getByInstallation(Long installationId) {
        return meterReadingRepository.findByInstallationId(installationId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    private void validateAntiForgeryPhotos(MeterReadingRequestDTO dto, MeterTypes meterType) {
        List<LocalDateTime> timestamps = dto.getPhotoTimestamps();

        long requiredCount = meterType.getPhotoCount();
        if (timestamps.size() != requiredCount) {
            throw new InvalidOperationException(
                    "Meter type '" + meterType.getMeterType() + "' requires exactly "
                            + requiredCount + " photos, but " + timestamps.size() + " were submitted");
        }

        int requiredIntervalSeconds = meterType.getPhoto_interval_seconds();
        List<LocalDateTime> sorted = timestamps.stream().sorted().collect(Collectors.toList());

        for (int i = 1; i < sorted.size(); i++) {
            long gapSeconds = Duration.between(sorted.get(i - 1), sorted.get(i)).getSeconds();
            if (gapSeconds < requiredIntervalSeconds) {
                throw new InvalidOperationException(
                        "Photos " + i + " and " + (i + 1) + " were taken " + gapSeconds
                                + "s apart, but at least " + requiredIntervalSeconds
                                + "s is required between photos to prevent forged/static readings");
            }
        }
    }
}
