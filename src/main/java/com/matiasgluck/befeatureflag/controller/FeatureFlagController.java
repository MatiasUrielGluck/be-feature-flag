package com.matiasgluck.befeatureflag.controller;

import com.matiasgluck.befeatureflag.dto.FeatureFlagDTO;
import com.matiasgluck.befeatureflag.dto.FeatureFlagResponseDTO;
import com.matiasgluck.befeatureflag.entity.FeatureFlag;
import com.matiasgluck.befeatureflag.service.FeatureFlagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/ff")
@RestController
public class FeatureFlagController {
    private final FeatureFlagService featureFlagService;

    public FeatureFlagController(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    @PostMapping()
    public ResponseEntity<FeatureFlagResponseDTO> createFeatureFlag(@RequestBody FeatureFlagDTO featureFlagDTO) {
        FeatureFlag createdFeatureFlag = featureFlagService.createFeatureFlag(featureFlagDTO);
        FeatureFlagResponseDTO response = FeatureFlagResponseDTO.builder()
                .id(createdFeatureFlag.getId())
                .name(createdFeatureFlag.getName())
                .isActive(createdFeatureFlag.getIsActive())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<FeatureFlagResponseDTO>> getFeatureFlags() {
        List<FeatureFlag> featureFlags = featureFlagService.getFeatureFlagsByUser();
        List<FeatureFlagResponseDTO> response = new ArrayList<>();
        featureFlags.forEach(ff -> response.add(FeatureFlagResponseDTO.builder()
                        .id(ff.getId())
                        .name(ff.getName())
                        .isActive(ff.getIsActive())
                        .build()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureFlagResponseDTO> getFeatureFlag(@PathVariable Integer id) {
        FeatureFlag featureFlag = featureFlagService.getFeatureFlagByIdAndUser(id);
        FeatureFlagResponseDTO response = FeatureFlagResponseDTO.builder()
                .id(featureFlag.getId())
                .name(featureFlag.getName())
                .isActive(featureFlag.getIsActive())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeatureFlagResponseDTO> updateFeatureFlag(@PathVariable Integer id, @RequestBody FeatureFlagDTO featureFlagDTO) {
        FeatureFlag updatedFeatureFlag = featureFlagService.updateFeatureFlag(id, featureFlagDTO);
        FeatureFlagResponseDTO response = FeatureFlagResponseDTO.builder()
                .id(updatedFeatureFlag.getId())
                .name(updatedFeatureFlag.getName())
                .isActive(updatedFeatureFlag.getIsActive())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
