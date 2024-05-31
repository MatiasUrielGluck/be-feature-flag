package com.matiasgluck.befeatureflag.repository;

import com.matiasgluck.befeatureflag.entity.FeatureFlag;
import com.matiasgluck.befeatureflag.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeatureFlagRepository extends CrudRepository<FeatureFlag, Integer> {
    Boolean existsByUserAndName(User user, String name);
    Boolean existsByUserAndNameAndIdNot(User user, String name, Integer id);
    List<FeatureFlag> findAllByUser(User user);
    Optional<FeatureFlag> findByUserAndId(User user, Integer id);
}
