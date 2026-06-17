package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.ServiceAreaRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.ServiceAreaResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Area;
import com.example.demo_Electricity_App.Entity.Master.ServiceArea;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Master.ServiceAreaMapper;
import com.example.demo_Electricity_App.Repository.Master.AreaRepository;
import com.example.demo_Electricity_App.Repository.Master.ServiceAreaRepository;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceAreaService {
    private final ServiceAreaRepository serviceAreaRepository;
    private final ServiceAreaMapper serviceAreaMapper;
    private final UsersRepository usersRepository;
    private final AreaRepository areaRepository;

    public ServiceAreaResponseDTO createServiceArea(@Valid ServiceAreaRequestDTO dto) {
        Area area = areaRepository.findById(dto.getAreaId())
                .orElseThrow(() -> new ResourceNotFoundException("Area not found: " + dto.getAreaId()));
        Users technician = usersRepository.findById(dto.getTechnicianId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Technician not found: " + dto.getTechnicianId()));
        Users biller = usersRepository.findById(dto.getBillerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Biller not found: " + dto.getBillerId()));

        ServiceArea sa = new ServiceArea();
        sa.setCode(dto.getCode());
        sa.setArea(area);
        sa.setTechnician(technician);
        sa.setBiller(biller);

        return serviceAreaMapper.toResponse(serviceAreaRepository.save(sa));
    }

    public ServiceAreaResponseDTO getServiceArea(Long id) {
        return serviceAreaMapper.toResponse(serviceAreaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area not found: " + id)));
    }

    public List<ServiceAreaResponseDTO> getServiceAreasByArea(Long areaId) {
        return serviceAreaRepository.findByAreaId(areaId).stream()
                .map(serviceAreaMapper::toResponse).collect(Collectors.toList());
    }

    public List<ServiceAreaResponseDTO> getAllServiceAreas() {
        return serviceAreaRepository.findAll().stream()
                .map(serviceAreaMapper::toResponse).collect(Collectors.toList());
    }
}
