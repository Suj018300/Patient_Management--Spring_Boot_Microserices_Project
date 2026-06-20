package com.pm.patient_service.mapper;

import com.pm.patient_service.dto.PatientRequestDto;
import com.pm.patient_service.dto.PatientResponseDto;
import com.pm.patient_service.entity.Patient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientMapper {

    private final ModelMapper modelMapper;

    public PatientResponseDto toDto(Patient patient) {
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    public Patient toEntity(PatientRequestDto patientRequestDto) {
        return modelMapper.map(patientRequestDto, Patient.class);
    }

}
