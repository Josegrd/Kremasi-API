package com.gardio.kremasi.entity;

import com.gardio.kremasi.constant.ERole;
import com.gardio.kremasi.constant.PathDb;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = PathDb.ROLE)
public class Role extends AuditEntity{

    @Enumerated(EnumType.STRING)
    private ERole role;
}
