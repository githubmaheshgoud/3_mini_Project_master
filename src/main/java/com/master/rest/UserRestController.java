package com.master.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.master.binding.LoginForm;
import com.master.binding.UnlockAccountForm;
import com.master.binding.UserForm;
import com.master.service.UserMgmtService;

@RestController
public class UserRestController {

	@Autowired
	private UserMgmtService userMgmtService;

	@PostMapping("/login")
	public ResponseEntity<String> loing(@RequestBody LoginForm loginForm) {
		String status = userMgmtService.login(loginForm);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@GetMapping("/countries")
	public Map<Integer, String> loadCountries() {
		return userMgmtService.getConutries();
	}

	@GetMapping("/state/{countryId}")
	public Map<Integer, String> loadStates(@PathVariable Integer countryId) {
		return userMgmtService.getCities(countryId);
	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> loadCities(@PathVariable Integer stateId) {
		return userMgmtService.getCities(stateId);

	}

	@GetMapping("/email/{email}")
	public String emailCheck(@PathVariable String email) {
		return userMgmtService.CheckEmail(email);

	}

	@PostMapping("/user")
	public ResponseEntity<String> userRegistration(@RequestBody UserForm userForm)  {
		String status = userMgmtService.registerUser(userForm);
		return new ResponseEntity<>(status, HttpStatus.CREATED);

	}

	@PostMapping("/unlock")
	public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccountForm unlockAccForm) {
		String status = userMgmtService.unlockAccount(unlockAccForm);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PostMapping("/forgotpwd/{email}")
	public ResponseEntity<String> forget(@PathVariable String email)  {
		String status = userMgmtService.forgotPwd(email);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

}
