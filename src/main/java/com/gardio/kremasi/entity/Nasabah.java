package com.gardio.kremasi.entity;

import com.gardio.kremasi.constant.NasabahStatus;
import com.gardio.kremasi.constant.PathDb;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = PathDb.NASABAH)
@Builder
public class Nasabah extends AuditEntity{

    private String fullName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String address;
    @Column(unique = true)
    private String nik;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false,updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date joinDate;

    @Enumerated(EnumType.STRING)
    private NasabahStatus status;

    @OneToOne
    @JsonIgnore
    private UserCredential userCredential;

    @PrePersist
    protected void onCreate(){
        joinDate = new Date();
    }
}
