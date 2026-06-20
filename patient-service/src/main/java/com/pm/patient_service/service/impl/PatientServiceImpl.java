package com.pm.patient_service.service.impl;

import com.pm.patient_service.dto.PatientRequestDto;
import com.pm.patient_service.dto.PatientResponseDto;
import com.pm.patient_service.entity.Patient;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.exception.PatientDoesNotExistsException;
import com.pm.patient_service.grpc.BillingServiceGrpcClient;
import com.pm.patient_service.kafka.KafkaProducer;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.repository.PatientRepository;
import com.pm.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final BillingServiceGrpcClient  billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    @Override
    public List<PatientResponseDto> getPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> patientMapper.toDto(patient))
                .toList();
    }

    @Override
    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email"
                            + patientRequestDto.getEmail()
                            + "already exists."
            );
        }
        Patient patient = patientMapper.toEntity(patientRequestDto);
        log.info("date of birth = " + patient.getDateOfBirth());
        patient.setRegisteredDate(LocalDate.now());
        log.info("registeredDate = " + patient.getRegisteredDate());
        Patient savedPatient = patientRepository.save(patient);
        billingServiceGrpcClient.createBillingAccount(savedPatient.getId().toString(), savedPatient.getName(), savedPatient.getEmail());
        kafkaProducer.sendEvent(patient);
        return  patientMapper.toDto(savedPatient);
    }

    @Override
    public PatientResponseDto getPatientById(
            UUID id
//            PatientRequestDto patientRequestDto
    ) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
            new PatientDoesNotExistsException(
                    "A patient with this id" + id + "does not exists."
            )
        );
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientResponseDto updatePatient(
            UUID id,
            PatientRequestDto patientRequestDto
    )  {
        if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), id)) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email" + patientRequestDto.getEmail() + "already exists."
            );
        }

        Patient existingPatient = patientRepository.findById(id).orElseThrow(
                () -> new PatientDoesNotExistsException(
                        "A patient with this id" + id + "does not exists."
                )
        );
        if(patientRequestDto.getName() != null) existingPatient.setName(patientRequestDto.getName());
        if(patientRequestDto.getAddress() != null) existingPatient.setAddress(patientRequestDto.getAddress());
        if(patientRequestDto.getDateOfBirth() != null) existingPatient.setDateOfBirth(patientRequestDto.getDateOfBirth());
//        if(patientRequestDto.getRegisteredDate() != null) existingPatient.setRegisteredDate(patientRequestDto.getRegisteredDate());

        return patientMapper.toDto(patientRepository.save(existingPatient));
    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }


}

