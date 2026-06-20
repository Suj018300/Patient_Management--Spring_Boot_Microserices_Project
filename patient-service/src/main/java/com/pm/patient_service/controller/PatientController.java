package com.pm.patient_service.controller;


import com.pm.patient_service.dto.CreatePatientValidationGroup;
import com.pm.patient_service.dto.PatientRequestDto;
import com.pm.patient_service.dto.PatientResponseDto;
import com.pm.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "API for managing Patients.")
public class PatientController {

//    @Autowired
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        List<PatientResponseDto> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(
            @Validated({CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDto patientRequestDto
    ) {
        PatientResponseDto patientResponseDto = patientService.createPatient(patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(
            @PathVariable UUID id
    ) {
        PatientResponseDto patientResponseDto = patientService.getPatientById(id);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable UUID id,
            @Valid @RequestBody PatientRequestDto patientRequestDto
    ) {
        PatientResponseDto patientResponseDto = patientService.updatePatient(id, patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }


}

