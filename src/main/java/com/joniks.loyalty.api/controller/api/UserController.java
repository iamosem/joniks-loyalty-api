package com.joniks.loyalty.api.controller.api;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joniks.loyalty.api.aop.HTTPRequestLogger;
import com.joniks.loyalty.api.constants.JLAConstants;
import com.joniks.loyalty.api.entity.User;
import com.joniks.loyalty.api.model.ListResult;
import com.joniks.loyalty.api.model.TransactionResult;
import com.joniks.loyalty.api.service.UserService;
import com.joniks.loyalty.api.utility.CommonUtility;
import com.joniks.loyalty.api.model.params.SocialUserParams;

import com.joniks.loyalty.api.logger.DebugManager;

@CrossOrigin
@RestController(value = "userController")
@RequestMapping(value = "/user")
public class UserController {
	private final DebugManager logger = DebugManager.getInstance(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private HTTPRequestLogger httpLogger;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUserList(User searchFilter, HttpServletResponse response) {
		System.out.println("THIS IS SEARCH FILTER - " + searchFilter.toString());
		ListResult users = userService.getUserList(searchFilter);
		response.addHeader(JLAConstants.HEADER_TOTAL_COUNT, Long.toString(users.getTotalResults()));
		return new ResponseEntity<>((List<User>) users.getResults(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public User getUserById(@PathVariable(name = "id") Integer userId) {
		return userService.getUserById(userId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void updateUserInfo(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

		httpLogger.logHttpRequest(request);

		if (userService.getUserById(user.getId()) == null) {
			response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_WARNING);
			response.addHeader(JLAConstants.APP_ALERT_MESSAGE, "The user does not exist.");
		} else if (bindingResult.hasErrors()) {
			response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_DANGER);
			List<FieldError> errors = bindingResult.getFieldErrors();
			StringBuilder message = new StringBuilder();
			for (FieldError error : errors) {
				message.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("\n");
			}
			response.addHeader(JLAConstants.APP_ALERT_MESSAGE, message.toString());
		} else {
			TransactionResult result = userService.updateUser(user, request);
			if (!result.isSuccess()) {
				response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_DANGER);
			} else {
				response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_SUCCESS);
			}
			response.addHeader(JLAConstants.APP_ALERT_MESSAGE, result.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/authenticate")
	public User authenticateUser(@RequestBody User param, HttpServletRequest request, HttpServletResponse response) {
		httpLogger.logHttpRequest(request, param);

		User user = null;

		try {
			user = userService.getUserByUsernameAndPassword(param.getUsername(), param.getPassword());
			if (CommonUtility.isObjectNull(user)) {
				user = userService.getUserByUsernameAndPassword(param.getUsername(), UUID.nameUUIDFromBytes((param.getPassword()).getBytes()).toString());
			}
			if (!(CommonUtility.isObjectNull(user))) {
				user.setPassword(""); // so that password is not reflected in request attributes & return object
				request.getSession().setAttribute("loggedIn", true);
				String source = user.toString() + "_" + System.currentTimeMillis();
				byte[] bytes = source.getBytes("UTF-8");
				String token = UUID.nameUUIDFromBytes(bytes).toString();
				request.getSession().setAttribute("SESSION_TOKEN", token);
				response.addHeader("SESSION_TOKEN", token);
				request.getSession().setAttribute("loggedInUser", user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/changepassword")
	public void changePassword(@RequestBody User param, HttpServletRequest request, HttpServletResponse response) {

		httpLogger.logHttpRequest(request, param);

		try {
			User user = userService.getUserByUsername(param.getUsername());
			if (!(CommonUtility.isObjectNull(user))) {
				user.setPassword(UUID.nameUUIDFromBytes((param.getPassword()).getBytes()).toString());
				userService.updateUserInfo(user);
				user.setPassword("");
			} else {
				response.addHeader(JLAConstants.APP_ALERT_MESSAGE, "The username you are trying to update does not exist.");
				response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_DANGER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/logout")
	public boolean logoutUser(HttpServletRequest request) {

		httpLogger.logHttpRequest(request);

		request.getSession().invalidate();
		return true;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void addUsers(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

		httpLogger.logHttpRequest(request, user);

		if (bindingResult.hasErrors()) {
			response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_DANGER);
			List<FieldError> errors = bindingResult.getFieldErrors();
			StringBuilder message = new StringBuilder();
			for (FieldError error : errors) {
				message.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("\n");
			}
			response.addHeader(JLAConstants.APP_ALERT_MESSAGE, message.toString());
		} else {
			TransactionResult result = userService.insertUser(user, request);
			if (!result.isSuccess()) {
				response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_DANGER);
			} else {
				response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_SUCCESS);
			}
			response.addHeader(JLAConstants.APP_ALERT_MESSAGE, result.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/info")
	public User getUserInfo(HttpServletRequest request) {
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		if (loggedInUser != null)
			return loggedInUser;
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/fb/info")
	public User getUserInfo(@RequestBody SocialUserParams socialUserParams, HttpServletRequest request, HttpServletResponse response) {

		httpLogger.logHttpRequest(request, socialUserParams);
		User user = null;
		try {
			user = userService.getUserByUsername(socialUserParams.getId());
			if (CommonUtility.isObjectNull(user)) {
				user = new User();
				user.setUsername(socialUserParams.getId());
				user.setEmail(socialUserParams.getEmail());
				user.setFirstName(socialUserParams.getFirstName());
				user.setLastName(socialUserParams.getLastName());
				user.setPassword(socialUserParams.getId());

				TransactionResult result = userService.insertUser(user, request);
				if (!result.isSuccess()) {
					response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_DANGER);
				} else {
					response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_SUCCESS);
					user = (User) result.getObj();
				}
				response.addHeader(JLAConstants.APP_ALERT_MESSAGE, result.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public void deletePatient(@PathVariable(name = "id") Integer userId, HttpServletRequest request, HttpServletResponse response) {

		httpLogger.logHttpRequest(request);

		TransactionResult result = userService.deleteUser(userId);
		if (!result.isSuccess()) {
			response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_DANGER);
		} else {
			response.addHeader(JLAConstants.APP_ALERT_TYPE, JLAConstants.APP_ALERT_TYPE_SUCCESS);
		}
		response.addHeader(JLAConstants.APP_ALERT_MESSAGE, result.getMessage());
	}

}
