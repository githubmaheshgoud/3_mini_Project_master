package com.master.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.master.binding.LoginForm;
import com.master.binding.UnlockAccountForm;
import com.master.binding.UserForm;
import com.master.entity.CityMaster;
import com.master.entity.CountryMaster;
import com.master.entity.StateMaster;
import com.master.entity.User;
import com.master.repository.CityRepository;
import com.master.repository.ContryRepository;
import com.master.repository.StateRepository;
import com.master.repository.UserRepository;
import com.master.utils.EmailUtils;

@Service
public class UserManagemantServiceImpl implements UserMgmtService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ContryRepository contryRepo;
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String CheckEmail(String email) {

		User user = userRepo.findByEmail(email);

		if (user == null) {
			return "UNIQUE";
		}
		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> getConutries() {

		List<CountryMaster> countries = contryRepo.findAll();

		Map<Integer, String> countryMap = new HashMap<>();

		countries.forEach(country -> countryMap.put(country.getCountryId(), country.getCountryName()));

		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {

		List<StateMaster> states = stateRepo.findByCountryId(countryId);

		Map<Integer, String> stateMap = new HashMap<>();

		states.forEach(state -> stateMap.put(state.getStateId(), state.getStateName()));

		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {

		List<CityMaster> cities = cityRepo.findByStateId(stateId);

		Map<Integer, String> cityMap = new HashMap<>();

		cities.forEach(citie -> cityMap.put(citie.getCityId(), citie.getCityName()));

		return cityMap;
	}

	@Override
	public String registerUser(UserForm user) {

		// copy data from binding obj to entity obj

		User entity = new User();
		BeanUtils.copyProperties(user, entity);

		// generate & Set Random pwd

		entity.setUserPwd(generateRandomPwd());

		// Set Account Status as Locked

		entity.setAccStatus("LOCKED");

		userRepo.save(entity);

		String to = user.getEmail();
		String subject = "Registion Email - Unlock Account ";
		String body = readEmailBody("REG_EMAIL_BODY.txt", entity);

		emailUtils.sendEmail(to, subject, body);

		return "User Account Created";
	}

	@Override
	public String unlockAccount(UnlockAccountForm unlockAccForm) {

		String email = unlockAccForm.getEmail();
		User user = userRepo.findByEmail(email);

		if (user != null && user.getUserPwd().equals(unlockAccForm.getTempPwd())) {

			user.setUserPwd(unlockAccForm.getNewPwd());
			user.setAccStatus("UnLocked");
			userRepo.save(user);
			return "Account Unlocked";

		}

		return "Invalid Temporary Password";
	}

	@Override
	public String login(LoginForm loginForm) {
		User user = userRepo.findByEmailAndUserPwd(loginForm.getEmail(), loginForm.getPwd());

		if (user == null) {
			return "Invalid Cradentials";
		}
		if (user.getAccStatus().equals("LOCKED")) {
			return "Account Locked";
		}
		return "SUCCESSS";
	}

	@Override
	public String forgotPwd(String email)   {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return "Your Email Id not Registered";

		}
		
		String subject = "Recover Password ";
		String body = readEmailBody("FORGOT.Html", user);
		System.out.println("body is printing" + body);
		emailUtils.sendEmail(email, subject, body);

		return "Password sent to registered email";
	}

	private String generateRandomPwd() {

		String text = "abcdefghijklmnopqrstuvwxyz123456789";

		StringBuilder sb = new StringBuilder();

		Random random = new Random();
		int pwdLength = 6;

		for (int i = 1; i <= pwdLength; i++) {

			int index = random.nextInt(text.length());
			sb.append(text.charAt(index));
		}

		return sb.toString();
	}

	private String readEmailBody(String fileName, User user) {
		StringBuffer sb = new StringBuffer();
		try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
			lines.forEach(line -> {
				line = line.replace("${FNAME}", user.getFName());
				line = line.replace(" ${LNAME}", user.getLName());
				line = line.replace("${TEMP_PWD}", user.getUserPwd());
				line = line.replace("${EMAIL}", user.getEmail());
				line = line.replace("${PWD}", user.getUserPwd());

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
