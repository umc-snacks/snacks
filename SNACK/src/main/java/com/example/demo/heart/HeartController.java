package com.example.demo.heart;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/heart/")
public class HeartController {
    private final HeartService heartService;

    @Autowired
    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    // TODO 예외처리: 이미 좋아요를 눌렀을 경우 안된다고 알려주기

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid HeartRequestDTO heartRequestDTO) throws Exception {
        heartService.insert(heartRequestDTO);
        return ResponseEntity.ok().body("a");
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody @Valid HeartRequestDTO heartRequestDTO) {
        heartService.delete(heartRequestDTO);
        return ResponseEntity.ok().body("a");

    }

}
