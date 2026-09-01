package com.ecommerce.demo.features.UserProfile.repository;

import com.ecommerce.demo.features.UserProfile.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findByUsername(String username);
}
