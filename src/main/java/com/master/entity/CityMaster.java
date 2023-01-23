package com.master.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="City_Master")
public class CityMaster {
	
	@Id
	private Integer cityId;
	private String cityName;
	private Integer stateId;
	

}
