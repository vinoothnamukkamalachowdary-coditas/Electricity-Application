package com.example.demo_Electricity_App.Repository;

import com.example.demo_Electricity_App.Entity.Master.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation,Long> {


    Optional<Invitation> findByInvitationToken(String invitationToken);

    Optional<Invitation> findByEmail(String email);

}
