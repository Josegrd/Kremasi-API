package com.enigmacamp.enigmacoop.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NasabahRequest {
    private String id;
    @NotBlank(message = "fullname is mandatory and cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$",message = "Fullname must contain only alphabet")
    private String fullName;

    @NotBlank(message = "Email is mandatory and cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone Number is mandatory and cannot be blank")
    @Pattern(regexp = "^[0-9]+$",message = "Phone number must containt only numbers")
    private String phoneNumber;

    @NotBlank(message = "Phone Number is mandatory and cannot be blank")
    private String address;

    @NotBlank(message = "NIK is mandatory and cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "NIK must contain only numbers")
    private String nik;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String username;
    private String password;
}
