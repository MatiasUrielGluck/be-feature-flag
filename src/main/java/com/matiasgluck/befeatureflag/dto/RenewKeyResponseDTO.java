package com.matiasgluck.befeatureflag.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RenewKeyResponseDTO {
    private String newKey;
}
