package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.CityRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.CityResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.City;
import com.example.demo_Electricity_App.Entity.Master.District;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Master.CityMapper;
import com.example.demo_Electricity_App.Repository.Master.CityRepository;
import com.example.demo_Electricity_App.Repository.Master.DistrictRepository;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    private DistrictRepository  districtRepository;
    private UsersRepository usersRepository;
    private CityMapper cityMapper;

    public CityResponseDTO createCity(@Valid CityRequestDTO dto) {
        District district = districtRepository.findById(dto.getDistrictId()).orElseThrow(()-> new RuntimeException("District not found"));
        Users cityHead = usersRepository.findById(dto.getCityHeadId()).orElseThrow(()-> new RuntimeException("City head not found"));
        City city = new City();
        city.setDistrict(district);
        city.setCityHead(cityHead);
        city.setId(city.getId());
        city.setName(dto.getName());
        city.setActive(true);
        return cityMapper.toCityResponse(cityRepository.save(city));
    }

    public CityResponseDTO getCity(Long id) {
        return cityMapper.toCityResponse(cityRepository.findById(id).orElseThrow(()-> new RuntimeException("City not found" + id)));
    }

    public List<CityResponseDTO> getCitiesByDistrict(Long districtId) {
        return cityRepository.findByDistrictId(districtId).stream().map(cityMapper::toCityResponse).collect(Collectors.toList());
    }

    public List<CityResponseDTO> getAllCities() {
        return cityRepository.findAll().stream()
                .map(cityMapper::toCityResponse).collect(Collectors.toList());
    }

    public CityResponseDTO updateCity(Long id, @Valid CityRequestDTO dto) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found: " + id));
        District district = districtRepository.findById(dto.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "District not found: " + dto.getDistrictId()));
        Users head = usersRepository.findById(dto.getCityHeadId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "City head user not found: " + dto.getCityHeadId()));
        city.setName(dto.getName());
        city.setDistrict(district);
        city.setCityHead(head);
        return cityMapper.toCityResponse(cityRepository.save(city));
    }

    public String deactivateCity(Long id) {
        City city =  cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found: " + id));
        city.setActive(false);
        cityRepository.save(city);
        return "City deactivated";
    }
}
