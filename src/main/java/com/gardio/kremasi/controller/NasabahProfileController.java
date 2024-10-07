package com.gardio.kremasi.controller;

import com.gardio.kremasi.constant.PathApi;
import com.gardio.kremasi.entity.Nasabah;
import com.gardio.kremasi.model.response.WebResponse;
import com.gardio.kremasi.service.NasabahService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathApi.BasePath.NASABAH_PROFILE)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CUSTOMER')")
@Slf4j
public class NasabahProfileController {
    private final NasabahService nasabahService;
    @GetMapping
    public ResponseEntity<?> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Nasabah nasabah = nasabahService.findByUsername(username);
        WebResponse<Nasabah> response = WebResponse.<Nasabah>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get Your Data")
                .data(nasabah)
                .build();
        return ResponseEntity.ok(response);
    }
}
