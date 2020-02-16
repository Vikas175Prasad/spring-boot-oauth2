package com.vikas.oauth.oauthserveroracle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikas.oauth.oauthserveroracle.model.User;


public interface UserDetailRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);
}
