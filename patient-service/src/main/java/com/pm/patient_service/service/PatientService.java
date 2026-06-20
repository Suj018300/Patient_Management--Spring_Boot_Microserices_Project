package com.pm.patient_service.service;


import com.pm.patient_service.dto.PatientRequestDto;
import com.pm.patient_service.dto.PatientResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface PatientService {

    List<PatientResponseDto> getPatients();

    PatientResponseDto createPatient(PatientRequestDto patientRequestDto);

    PatientResponseDto getPatientById(UUID id);

    PatientResponseDto updatePatient(UUID id, PatientRequestDto patientRequestDto);

    void deletePatient(UUID id);

}