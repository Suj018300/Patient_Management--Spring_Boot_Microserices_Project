package com.pm.patient_service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceeds 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Date of birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

//    @NotBlank(
//            groups = CreatePatientValidationGroup.class,
//            message = "Registered date is required")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDate registeredDate;

}
