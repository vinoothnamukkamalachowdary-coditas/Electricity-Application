package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.DistrictRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.DistrictResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.District;
import com.example.demo_Electricity_App.Entity.Master.State;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Master.DistrictMapper;
import com.example.demo_Electricity_App.Repository.Master.DistrictRepository;
import com.example.demo_Electricity_App.Repository.Master.StateRepository;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DistrictService {
    private final DistrictRepository districtRepo;
    private final DistrictMapper mapper;
    private final UsersRepository usersRepo;
    private final StateRepository stateRepo;

    public DistrictResponseDTO createDistrict(DistrictRequestDTO dto) {
        State state = stateRepo.findById(dto.getStateId())
                .orElseThrow(() -> new ResourceNotFoundException("State not found: " + dto.getStateId()));
        Users districtHead = usersRepo.findById(dto.getDistrictHeadId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "District head user not found: " + dto.getDistrictHeadId()));

        District district = new District();
        district.setDistrictName(dto.getDistrictName());
        district.setActive(true);
        district.setState(state);
        district.setDistrictHead(districtHead);

        return mapper.toResponse(districtRepo.save(district));

    }

    public DistrictResponseDTO getDistrict(Long id) {
        return mapper.toResponse(districtRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("District not found: " + id)));
    }

    public List<DistrictResponseDTO> getAllDistrict() {
        return districtRepo.findAll().stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    public DistrictResponseDTO updateDistrict(Long id, @Valid DistrictRequestDTO dto) {
        District district = districtRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("District not found: " + id));
        State state = stateRepo.findById(dto.getStateId())
                .orElseThrow(() -> new ResourceNotFoundException("State not found: " + dto.getStateId()));
        Users head = usersRepo.findById(dto.getDistrictHeadId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "District head user not found: " + dto.getDistrictHeadId()));
        district.setDistrictName(dto.getDistrictName());
        district.setState(state);
        district.setDistrictHead(head);
        return mapper.toResponse(districtRepo.save(district));
    }

    public List<DistrictResponseDTO> getDistrictByState(Long stateId) {
        return districtRepo.findDistrictByStateId(stateId).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }
}
