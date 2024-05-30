package com.matiasgluck.befeatureflag.repository;

import com.matiasgluck.befeatureflag.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
    public Boolean existsByApiKey(String apiKey);
}
