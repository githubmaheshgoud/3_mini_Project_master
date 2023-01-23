package com.master.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="State_Master")
public class StateMaster {
	@Id
	private Integer stateId;
	private String stateName;
	private Integer countryId;


}