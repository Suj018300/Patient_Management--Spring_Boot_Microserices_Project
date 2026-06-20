package com.pm.patient_service.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDto {

    private String id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
    private String registeredDate;

}
