package com.example.demo.heart;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid HeartRequestDTO heartRequestDTO) throws Exception {
        heartService.insert(heartRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody @Valid HeartRequestDTO heartRequestDTO) {
        heartService.delete(heartRequestDTO);
        return ResponseEntity.ok().build();

    }

}
