package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.CityResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    public CityResponseDTO toCityResponse(City city) {
        CityResponseDTO cityResponseDTO = new CityResponseDTO();
        cityResponseDTO.setId(city.getId());
        cityResponseDTO.setName(city.getName());
        cityResponseDTO.setActive(city.isActive());
        if (city.getDistrict() != null) {
            cityResponseDTO.setDistrictId(city.getDistrict().getId());
            cityResponseDTO.setDistrictName(city.getDistrict().getDistrictName());
        }
        if (city.getCityHead() != null) {
            cityResponseDTO.setCityHeadId(city.getCityHead().getId());
            cityResponseDTO.setCityHeadName(city.getCityHead().getName());
        }
        return cityResponseDTO;
    }
}
