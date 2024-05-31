package com.matiasgluck.befeatureflag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FeatureFlagResponseDTO {
    private int id;
    private String name;
    private Boolean isActive;
}
