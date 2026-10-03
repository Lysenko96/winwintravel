package web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.config.JwtTokenUtil;
import web.dto.JwtResponse;
import web.model.ProcessingLog;
import web.model.User;
import web.repository.ProcessingLogRepository;
import web.repository.UserRepository;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DataController {

    private final ProcessingLogRepository processingLogRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @PostMapping("/transform")
    public ResponseEntity<?> transform(@RequestBody JwtResponse response) {
        log.info("##TRANSFORM");
        User user = userRepository.findByEmail(jwtTokenUtil.getUsername(response.getToken()));
        String reverse = new StringBuilder(response.getToken()).reverse().toString();
        processingLogRepository.save(new ProcessingLog(user, response.getToken(), reverse));
        response.setToken(reverse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
