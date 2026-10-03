package web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DataController {

    @GetMapping("/transform")
    public ResponseEntity<?> transform() {
        log.info("##TRANSFORM");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
