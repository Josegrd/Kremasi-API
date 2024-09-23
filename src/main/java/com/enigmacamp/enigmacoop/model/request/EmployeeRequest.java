package com.enigmacamp.enigmacoop.model.request;

import com.enigmacamp.enigmacoop.constant.PositionEmployee;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest {
    private String id;
    @NotBlank(message = "fullname is mandatory and cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$",message = "Fullname must contain only alphabet")
    private String fullName;

    @NotBlank(message = "Email is mandatory and cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^[0-9]+$",message = "Phone number must containt only numbers")
    @NotBlank(message = "Phone Number is mandatory and cannot be blank")
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    private PositionEmployee position;

    private Long salary;

    private Boolean status;

    private MultipartFile image;

    @Override
    public String toString() {
        return "EmployeeRequest{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hireDate=" + hireDate +
                ", position=" + position +
                ", salary=" + salary +
                ", image=" + image +
                '}';
    }
}
