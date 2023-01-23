package com.master.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.master.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable> {

	
	public User findByEmail(String Email);
	
	
	public User findByEmailAndUserPwd(String email,String pwd);
}
