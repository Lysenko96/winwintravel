package com.winwintravel.auth.controller;

import com.winwintravel.auth.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProcessController {

    private final RestClient restClient = RestClient.create();
    private final JwtResponse jwtResponse;

    @PostMapping("/process")
    public ResponseEntity<?> process() {
        log.info("##PROCESS");
        log.info(jwtResponse.getToken());
        JwtResponse response = restClient.post().uri("http://localhost:8081/api/transform")
                .header("Authorization", "Bearer " + jwtResponse.getToken())
                .body(new JwtResponse(jwtResponse.getToken()))
                .retrieve().body(JwtResponse.class);
        log.info("{}", response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
