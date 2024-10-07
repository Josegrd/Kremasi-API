package com.gardio.kremasi.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtClaim{
    private String userId;
    private List<String> roles;
}
