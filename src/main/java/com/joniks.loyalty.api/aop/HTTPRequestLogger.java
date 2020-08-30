package com.joniks.lotalty.api.aop;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joniks.lotalty.api.entity.AuditLog;
import com.joniks.lotalty.api.logger.DebugManager;
import com.joniks.lotalty.api.repository.AuditLogRepository;
import com.joniks.lotalty.api.utility.JSONConverter;
import com.joniks.lotalty.api.utility.SessionUtility;
import com.joniks.lotalty.api.utility.StackTraceUtility;

@Component
public class HTTPRequestLogger {

	@Autowired
	private AuditLogRepository auditLogRepository;

	private final DebugManager logger = DebugManager.getInstance(HTTPRequestLogger.class);

	public void logHttpRequest(HttpServletRequest request) {
		logHttpRequest(request, null);
	}

	public void logHttpRemoteRequest(HttpServletRequest request) {
		try {
			StringBuilder builder = new StringBuilder();

			String url = request.getRequestURL().toString(), method = request.getMethod(), payload = "";

			StringBuilder payloadSB = new StringBuilder("{");
			builder.append("(" + request.getMethod() + ") " + request.getRequestURL().toString());
			if (request.getParameterNames().hasMoreElements()) {
				builder.append(" - ");
				Enumeration enumeration = request.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String parameterName = (String) enumeration.nextElement();
					builder.append("(").append(parameterName).append("=").append(request.getParameter(parameterName)).append("), ");
					payloadSB.append("(").append(parameterName).append("=").append(request.getParameter(parameterName)).append("), ");
				}
			}
			payloadSB.append("(" + request.getRemoteHost() + "," + request.getRemoteAddr() + "," + request.getRemotePort() + ")");
			payloadSB.append("}");
			payload = payloadSB.toString();
			AuditLog auditLog = new AuditLog();
			auditLog.setDate(new Date());
			auditLog.setPayload(payload);
			auditLog.setMethod(method);
			auditLog.setUrl(url);
			auditLog.setUser(0);
			logger.debug("THIS IS AUDIT LOG TO SAVE " + JSONConverter.convertObjectToJson(auditLog));
			auditLogRepository.saveAndFlush(auditLog);

		} catch (Exception e) {
			logger.debug(StackTraceUtility.convertStackTraceToString(e));
		}
	}

	public void logHttpRequest(HttpServletRequest request, Object requestBody) {
		try {
			StringBuilder builder = new StringBuilder();

			String url = request.getRequestURL().toString(), method = request.getMethod(), payload = "";

			StringBuilder payloadSB = new StringBuilder("{");
			builder.append("(" + request.getMethod() + ") " + request.getRequestURL().toString());
			if (request.getParameterNames().hasMoreElements()) {
				builder.append(" - ");
				Enumeration enumeration = request.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String parameterName = (String) enumeration.nextElement();
					builder.append("(").append(parameterName).append("=").append(request.getParameter(parameterName)).append("), ");
					payloadSB.append("(").append(parameterName).append("=").append(request.getParameter(parameterName)).append("), ");
				}
			}
			if (requestBody != null) {
				payloadSB.append(", requestBody=" + JSONConverter.convertObjectToJson(requestBody));
			}
			payloadSB.append("}");
			payload = payloadSB.toString();
			AuditLog auditLog = new AuditLog();
			auditLog.setDate(new Date());
			auditLog.setPayload(payload);
			auditLog.setMethod(method);
			auditLog.setUrl(url);
			auditLog.setUser(SessionUtility.getCurrentUserId(request));
			logger.debug("THIS IS AUDIT LOG TO SAVE " + JSONConverter.convertObjectToJson(auditLog));
			auditLogRepository.saveAndFlush(auditLog);

		} catch (Exception e) {
			logger.debug(StackTraceUtility.convertStackTraceToString(e));
		}
	}

}