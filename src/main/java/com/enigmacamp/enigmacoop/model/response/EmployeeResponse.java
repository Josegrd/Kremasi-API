package com.enigmacamp.enigmacoop.model.response;

import com.enigmacamp.enigmacoop.constant.PositionEmployee;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    private PositionEmployee position;
    private Long salary;
    private Boolean status;
    private ImageResponse imageResponse;
}
