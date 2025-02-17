package com.gardio.kremasi.entity;

import com.gardio.kremasi.constant.PathDb;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = PathDb.IMAGE)
public class Image extends AuditEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "content_type", nullable = false)
    private String contentType;
}
