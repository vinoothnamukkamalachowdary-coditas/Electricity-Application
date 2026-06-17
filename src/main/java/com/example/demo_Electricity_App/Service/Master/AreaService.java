package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.AreaRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.AreaResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.Area;
import com.example.demo_Electricity_App.Entity.Master.City;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Master.AreaMapper;
import com.example.demo_Electricity_App.Repository.Master.AreaRepository;
import com.example.demo_Electricity_App.Repository.Master.CityRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AreaService {
    private final AreaRepository areaRepository;
    private final AreaMapper mapper;
    private final CityRepository repo;

    public AreaResponseDTO addArea(@Valid AreaRequestDTO dto) {
        City city = repo.findById(dto.getCityID()).orElseThrow(() -> new ResourceNotFoundException("City not found" +  dto.getCityID()));
        Area area = new Area();
        area.setAreaCode(dto.getAreaCode());
        area.setCity(city);
        area.setName(dto.getAreaName());
        area.setActive(true);
        return mapper.toResponse(areaRepository.save(area));
    }

    public AreaResponseDTO getAreaById(Long id) {
        return mapper.toResponse(areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Area not found: " + id)));
    }

    public List<AreaResponseDTO> getAllAreas() {
        return areaRepository.findAll().stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<AreaResponseDTO> getAreasByCity(Long cityId) {
        return areaRepository.findByCityId(cityId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
