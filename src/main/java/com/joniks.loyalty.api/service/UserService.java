package com.joniks.lotalty.api.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joniks.lotalty.api.entity.User;
import com.joniks.lotalty.api.model.ListResult;
import com.joniks.lotalty.api.model.TransactionResult;
import com.joniks.lotalty.api.repository.UserRepository;
import com.joniks.lotalty.api.utility.CommonUtility;
import com.joniks.lotalty.api.utility.SessionUtility;

@Service("userService")
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	private void initializeUsers() {
		SessionUtility.reInitialize(userRepository);
	}

	public ListResult getUserList(User searchFilter) {
		List<User> userList = userRepository.listUsers(searchFilter);
		Long userListCount = userRepository.countUsers(searchFilter);
		if (!CommonUtility.isListNullOrEmpty(userList)) {
			for (User user : userList) {
				System.out.println("User - " + user.toString());
				user.setPassword("");
			}
		}
		return new ListResult(userList, userListCount);
	}

	public TransactionResult insertUser(User user, HttpServletRequest request) {
		TransactionResult result = new TransactionResult();
		User userFromDb = userRepository.findOneByUsernameAndDeleted(user.getUsername(), false);

		if (userFromDb != null) {
			result.setMessage("Username '" + user.getUsername() + "' is already used.");
			result.setSuccess(false);
		} else {

			Date currentDate = new Date();
			user.setCreatedDate(currentDate);
			user.setModifiedDate(currentDate);
			user.setCreatedBy(SessionUtility.getCurrentUserId(request));
			user.setModifiedBy(SessionUtility.getCurrentUserId(request));
			user.setPassword(UUID.nameUUIDFromBytes((user.getPassword()).getBytes()).toString());
			user.setUploaded(false);
			User insertedUser = userRepository.saveAndFlush(user);
			if (insertedUser != null) {
				result.setMessage("Successfully inserted '" + user.getUsername() + "'.");
				result.setSuccess(true);
			} else {
				result.setMessage("Insertion of  '" + user.getUsername() + "' failed.");
				result.setSuccess(false);
			}
		}

		return result;

	}

	public User getUserById(Integer userId) {
		return userRepository.findOneByIdAndDeleted(userId, false);
	}

	public User updateUserInfo(User user) {
		User fromDBUser = userRepository.findOneByIdAndDeleted(user.getId(), false);
		fromDBUser.setInfo(user);
		fromDBUser.setUploaded(false);
		User updatedUser = userRepository.saveAndFlush(fromDBUser);

		SessionUtility.reInitialize(userRepository);
		return updatedUser;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		return userRepository.findOneByUsernameAndPasswordAndDeleted(username, password, false);
	}

	public User getUserByUsername(String username) {
		return userRepository.findOneByUsernameAndDeleted(username, false);
	}

	public TransactionResult updateUser(User user, HttpServletRequest request) {
		TransactionResult result = new TransactionResult();
		User userToUpdate = userRepository.findOneByIdAndDeleted(user.getId(), false);

		if (userToUpdate == null) {
			result.setMessage("The user you are trying to update does not exist.");
			result.setSuccess(false);
		} else {
			userToUpdate.update(user);
			userToUpdate.setModifiedBy(SessionUtility.getCurrentUserId(request));
			userToUpdate.setModifiedDate(new Date());
			userToUpdate.setUploaded(false);
			User updatedUser = userRepository.saveAndFlush(userToUpdate);
			if (updatedUser != null) {
				result.setMessage("Successfully updated '" + user.getLastName() + ", " + user.getFirstName() + "'.");
				result.setSuccess(true);
			} else {
				result.setMessage("Update of  '" + user.getLastName() + ", " + user.getFirstName() + "' failed.");
				result.setSuccess(false);
			}
		}
		return result;
	}

	public TransactionResult deleteUser(Integer userId) {
		TransactionResult result = new TransactionResult();
		User userToRemove = userRepository.findOneByIdAndDeleted(userId, false);

		if (userToRemove == null) {
			result.setMessage("The user you are trying to delete does not exist.");
			result.setSuccess(false);
		} else if (userId == -1) {
			result.setMessage("The user you are trying to delete cannot be removed.");
			result.setSuccess(false);
		} else {
			userRepository.deleteById(userId);
			result.setMessage("Successfully removed user.");
			result.setSuccess(true);
		}
		return result;
	}

}
