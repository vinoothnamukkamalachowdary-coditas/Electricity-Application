package com.example.demo_Electricity_App.Service.Master;

import com.example.demo_Electricity_App.DTO.Master.Request.StateRequestDTO;
import com.example.demo_Electricity_App.DTO.Master.Response.StateResponseDTO;
import com.example.demo_Electricity_App.Entity.Master.State;
import com.example.demo_Electricity_App.Entity.Master.Users;
import com.example.demo_Electricity_App.Exception.ResourceNotFoundException;
import com.example.demo_Electricity_App.Mapper.Master.StateMapper;
import com.example.demo_Electricity_App.Repository.Master.StateRepository;
import com.example.demo_Electricity_App.Repository.Master.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;
    private final UsersRepository usersRepo;
    public StateResponseDTO createState(StateRequestDTO stateRequestDTO) {
        Users stateHead = usersRepo.findById(stateRequestDTO.getStateHeadId()).orElseThrow(() -> new ResourceNotFoundException("State Head not found" + stateRequestDTO.getStateHeadId()));
        State state = new State();
        state.setName(stateRequestDTO.getName());
        state.set_Active(true);
        state.setStateHead(stateHead);
        return stateMapper.responseDTO(stateRepository.save(state));
    }
}
