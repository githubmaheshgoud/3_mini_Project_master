package com.master.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Country_Master")
@Data
public class CountryMaster {

  @Id
  private Integer countryId;		
  private String countryName; 
	
	
}