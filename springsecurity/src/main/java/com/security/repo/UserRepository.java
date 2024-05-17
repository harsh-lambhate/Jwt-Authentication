package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUserName(String userName);
}
