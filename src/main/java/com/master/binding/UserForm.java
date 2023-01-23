package com.master.binding;

import java.util.Date;

import lombok.Data;

@Data
public class UserForm {

	
	private String fName;
	private String lName;
	private String email;
	private int phno;
	private Date dob;
	private String gender;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
}
