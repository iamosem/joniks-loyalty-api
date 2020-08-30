package com.joniks.loyalty.api.interceptors;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.joniks.loyalty.api.constants.JLAConstants;
import com.joniks.loyalty.api.logger.DebugManager;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	private final DebugManager logger = DebugManager.getInstance(SessionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("PRE HANDLE OF Request URL: " + request.getServletPath() + " | " + request.getMethod());

		String sessionToken = (String) request.getSession().getAttribute("SESSION_TOKEN");

		// CORS [start]
		// response.setHeader("Access-Control-Allow-Origin", "http://" + getCurrentHost());
		response.setHeader("Access-Control-Allow-Origin", "https://localhost:4200");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe, SESSION_TOKEN");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Expose-Headers", JLAConstants.VALUE_EXPOSE_HEADERS);
		// CORS [end]

		if (request.getMethod().equals("OPTIONS") || request.getServletPath().equals("/user/fb/info") || request.getServletPath().equals("/user/authenticate") || request.getServletPath().equals("/user/logout") || request.getServletPath().startsWith("/mirror") || request.getServletPath().startsWith("/certificate")) {
			return true;
		}

		if (request.getSession() != null && sessionToken != null) {
			if (request.getHeader("SESSION_TOKEN") != null && request.getHeader("SESSION_TOKEN").equals(sessionToken)) {
				response.addHeader("SESSION_TOKEN", request.getSession().getAttribute("SESSION_TOKEN").toString());
				logger.debug("SETTING RESPONSE HEADER SESSION_TOKEN TO " + request.getSession().getAttribute("SESSION_TOKEN").toString());
			} else {
				logger.debug("NAA KO DIRI 3");
				logger.debug("REQUEST HEADER FOR SESSION_TOKEN IS NOT EQUIVALENT WITH THE CURRENT USER's SESSION_TOKEN " + sessionToken);
				response.addHeader("SESSION_TOKEN", "INVALID_SESSION");
				response.setStatus(401);
				return false;
			}
		} else {
			logger.debug("NAA KO DIRI 2");
			response.addHeader("SESSION_TOKEN", "INVALID_SESSION");
			response.setStatus(401);
			return false;
		}
		return true;
	}

	private String getCurrentHost() {
		String currentHost = "";
		try {
			currentHost = InetAddress.getLocalHost().getHostAddress();
			if (!StringUtils.isEmpty(currentHost)) {
				String host = InetAddress.getByName(currentHost).getHostName();
				if (!StringUtils.isEmpty(host))
					currentHost = host;
			}
		} catch (Exception e) {
			currentHost = "joniksloyalty.com";
		}
		return currentHost;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}
}
