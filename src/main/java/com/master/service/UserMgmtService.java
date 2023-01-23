package com.master.service;

import java.util.Map;

import com.master.binding.LoginForm;
import com.master.binding.UnlockAccountForm;
import com.master.binding.UserForm;

public interface UserMgmtService {

	public String CheckEmail(String email);

	public Map<Integer, String> getConutries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public String registerUser(UserForm user);

	public String unlockAccount(UnlockAccountForm accForm);

	public String login(LoginForm loginForm);

	public String forgotPwd(String email);


}
