package com.joniks.loyalty.api.utility;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.joniks.loyalty.api.entity.User;
import com.joniks.loyalty.api.repository.UserRepository;

public class SessionUtility {

	private static HashMap<Integer, User> userInTable = new HashMap<Integer, User>();

	public static int getCurrentUserId(HttpServletRequest request) {

		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		if (loggedInUser != null)
			return loggedInUser.getId();
		return -1;
	}

	public static void reInitialize(UserRepository userRepository) {
		userInTable = new HashMap<Integer, User>();
		List<User> userList = userRepository.findAll();
		if (userList != null) {
			for (User user : userList) {
				userInTable.put(user.getId(), user);
			}
		}
	}

	public static User getUser(int userId) {
		User user = userInTable.get(userId);
		if (user == null) {
			user = new User();
			user.setFirstName("UNKNOWN");
			user.setLastName("USER");
		}
		return user;
	}
}
