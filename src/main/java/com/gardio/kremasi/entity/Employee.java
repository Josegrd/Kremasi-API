package com.gardio.kremasi.entity;

import com.gardio.kremasi.constant.PathDb;
import com.gardio.kremasi.constant.PositionEmployee;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = PathDb.EMPLOYEE)
public class Employee extends AuditEntity{

    private String fullName;
    private String email;
    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss a", timezone = "Asia/Jakarta")
    private Date hireDate;

    @Enumerated(EnumType.STRING)
    private PositionEmployee position;
    private Long salary;
    @Column(columnDefinition = "boolean default true")
    private Boolean status = true;

    @OneToOne
    @JoinColumn(name = "image_id", unique = true)
    private Image image;

}
