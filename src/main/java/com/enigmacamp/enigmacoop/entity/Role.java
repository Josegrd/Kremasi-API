package com.enigmacamp.enigmacoop.entity;

import com.enigmacamp.enigmacoop.constant.ERole;
import com.enigmacamp.enigmacoop.constant.PathDb;
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
