package com.matiasgluck.befeatureflag.service;

import com.matiasgluck.befeatureflag.dto.FeatureFlagDTO;
import com.matiasgluck.befeatureflag.entity.FeatureFlag;
import com.matiasgluck.befeatureflag.entity.User;
import com.matiasgluck.befeatureflag.exception.EntityAlreadyExistsException;
import com.matiasgluck.befeatureflag.repository.FeatureFlagRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureFlagService {
    private final FeatureFlagRepository featureFlagRepository;
    private final UserService userService;

    public FeatureFlagService(FeatureFlagRepository featureFlagRepository, UserService userService) {
        this.featureFlagRepository = featureFlagRepository;
        this.userService = userService;
    }

    public FeatureFlag createFeatureFlag(FeatureFlagDTO featureFlagDTO) {
        User user = userService.getUserInstance();
        if (featureFlagRepository.existsByUserAndName(user, featureFlagDTO.getName())) throw new EntityAlreadyExistsException("Feature flag already exists");
        FeatureFlag featureFlag = FeatureFlag.builder()
                .name(featureFlagDTO.getName())
                .isActive(featureFlagDTO.getIsActive())
                .user(userService.getUserInstance())
                .build();
        return featureFlagRepository.save(featureFlag);
    }

    public List<FeatureFlag> getFeatureFlagsByUser() {
        User user = userService.getUserInstance();
        return featureFlagRepository.findAllByUser(user);
    }

    public FeatureFlag getFeatureFlagByIdAndUser(Integer id) {
        User user = userService.getUserInstance();
        return featureFlagRepository.findByUserAndId(user, id).orElseThrow(() -> new EntityNotFoundException("Feature flag not found"));
    }

    public FeatureFlag updateFeatureFlag(Integer id, FeatureFlagDTO featureFlagDTO) {
        User user = userService.getUserInstance();
        FeatureFlag featureFlag = featureFlagRepository.findByUserAndId(user, id).orElseThrow(() -> new EntityNotFoundException("Feature flag not found"));
        if (featureFlagRepository.existsByUserAndNameAndIdNot(user, featureFlagDTO.getName(), id)) {
            throw new EntityAlreadyExistsException("A feature flag called '" + featureFlagDTO.getName() + "' already exists.");
        }
        if (featureFlagDTO.getName() != null) featureFlag.setName(featureFlagDTO.getName());
        if (featureFlagDTO.getIsActive() != null) featureFlag.setIsActive(featureFlagDTO.getIsActive());
        return featureFlagRepository.save(featureFlag);
    }
}
