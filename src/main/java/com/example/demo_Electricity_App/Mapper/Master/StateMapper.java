package com.example.demo_Electricity_App.Mapper.Master;

import com.example.demo_Electricity_App.DTO.Master.Response.StateResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.State;
import org.springframework.stereotype.Component;

@Component
public class StateMapper {
    public StateResponseDTO responseDTO(State state) {
        StateResponseDTO stateResponseDTO = new StateResponseDTO();
        stateResponseDTO.setId(state.getId());
        stateResponseDTO.setName(state.getName());
        stateResponseDTO.setActive(state.is_Active());
        if (state.getStateHead() != null) {
            stateResponseDTO.setStateHeadId(state.getStateHead().getId());
            stateResponseDTO.setStateHeadName(state.getStateHead().getName());
        }
        return stateResponseDTO;
    }
}



