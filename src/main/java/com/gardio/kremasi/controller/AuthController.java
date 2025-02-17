package com.gardio.kremasi.controller;


import com.gardio.kremasi.constant.PathApi;
import com.gardio.kremasi.model.request.AuthRequest;
import com.gardio.kremasi.model.request.NasabahRequest;
import com.gardio.kremasi.model.response.LoginResponse;
import com.gardio.kremasi.model.response.NasabahResponse;
import com.gardio.kremasi.model.response.WebResponse;
import com.gardio.kremasi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathApi.BasePath.AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    @PostMapping(PathApi.SubBashPath.SIGNUP)
    public ResponseEntity<WebResponse<NasabahResponse>> registerNasabah(
            @Valid @RequestBody NasabahRequest nasabahRequest){
        NasabahResponse nasabahResponse = authService.register(nasabahRequest);
        WebResponse<NasabahResponse> response = WebResponse.<NasabahResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success register new nasabah")
                .data(nasabahResponse)
                .build();
        return ResponseEntity.ok(response);
    }
//    @PostMapping(PathApi.SubBashPath.SIGNIN)
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        log.info("Login Request: {}", authRequest);
        LoginResponse token = authService.login(authRequest);
        WebResponse<LoginResponse> response = WebResponse.<LoginResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Login")
                .data(token)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(PathApi.SubBashPath.CHECK_EMAIL)
    public ResponseEntity<?> checkEmail(@RequestParam String email, @RequestParam String username){
        Boolean result = authService.checkEmailUsername(username, email);
        WebResponse<Boolean> response = WebResponse.<Boolean>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success check email")
                .data(result)
                .build();
        return ResponseEntity.ok(response);
    }
}
