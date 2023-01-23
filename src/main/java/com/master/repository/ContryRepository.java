package com.master.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.master.entity.CountryMaster;

public interface ContryRepository extends JpaRepository<CountryMaster, Serializable> {

}
