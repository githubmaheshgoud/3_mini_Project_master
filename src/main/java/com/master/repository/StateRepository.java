package com.master.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.master.entity.StateMaster;

public interface StateRepository extends JpaRepository<StateMaster, Serializable> {

	public List<StateMaster> findByCountryId(Integer countryId);

	
	
}
