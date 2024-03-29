package com.joniks.loyalty.api.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joniks.loyalty.api.utility.StackTraceUtility;

/**
 * DebugManager is a wrapper class used for debugging. It makes use of the log4j
 * library.
 * 
 * @author geeta
 */
public class DebugManager {
	/**
	 * logger to be used for debugging
	 */
	private Logger logger = null;

	/**
	 * Construct a new debug manager for logging using the given class.
	 * 
	 * @param c the class
	 */
	private DebugManager(Class<?> c) {
		logger = LoggerFactory.getLogger(c);
	}

	/**
	 * Construct a new debug manager for logging using the given string.
	 * 
	 * @param s the string representation of the logger (usually a fully qualified
	 *          class name).
	 */
	private DebugManager(String s) {
		logger = LoggerFactory.getLogger(s);
	}

	/**
	 * Get a debug manager for the given class.
	 * 
	 * @param c the class
	 * @return the debug manager
	 */
	public static DebugManager getInstance(Class<?> c) {
		return new DebugManager(c);
	}

	/**
	 * Get a debug manager for the given class name
	 * 
	 * @param s the class name
	 * @return the debug manager
	 */
	public static DebugManager getInstance(String s) {
		return new DebugManager(s);
	}

	/**
	 * Get the created logger.
	 * 
	 * @return the logger.
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Generate a debug-level message.
	 * 
	 * @param e            the exception
	 * @param exceptionCnt the unique exception number within the enclosing class.
	 */
	public void debug(Exception e, String exceptionCnt) {
		logger.debug(e.getMessage(), new Throwable(logger.getName() + "_" + exceptionCnt));
	}

	/**
	 * Generate a debug-level message.
	 * 
	 * @param s the string to print
	 */
	public void debug(String s) {
		logger.debug(s);
	}

	/**
	 * Generate a debug-level message.
	 * 
	 * @param s the string to print
	 */
	public void debug(Exception e) {
		logger.debug(StackTraceUtility.convertStackTraceToString(e));
	}

	/**
	 * Generate a info-level message.
	 * 
	 * @param e            the exception
	 * @param exceptionCnt the unique exception number within the enclosing class.
	 */
	public void info(Exception e, String exceptionCnt) {
		logger.info(e.getMessage(), new Throwable(logger.getName() + "_" + exceptionCnt));
	}

	/**
	 * Generate a info-level message.
	 * 
	 * @param s the string to print
	 */
	public void info(String s) {
		logger.info(s);
	}

	/**
	 * Generate a info-level message.
	 * 
	 * @param s the string to print
	 */
	public void info(Exception e) {
		logger.info(StackTraceUtility.convertStackTraceToString(e));
	}

	/**
	 * Generate a warn-level message.
	 * 
	 * @param e            the exception
	 * @param exceptionCnt the unique exception number within the enclosing class.
	 */
	public void warn(Exception e, String exceptionCnt) {
		logger.warn(e.getMessage(), new Throwable(logger.getName() + "_" + exceptionCnt));
	}

	/**
	 * Generate a warn-level message.
	 * 
	 * @param s the string to print
	 */
	public void warn(String s) {
		logger.warn(s);
	}

	/**
	 * Generate a warn-level message.
	 * 
	 * @param s the string to print
	 */
	public void warn(Exception e) {
		logger.warn(StackTraceUtility.convertStackTraceToString(e));
	}

	/**
	 * Generate a error-level message.
	 * 
	 * @param e            the exception
	 * @param exceptionCnt the unique exception number within the enclosing class.
	 */
	public void error(Exception e, String exceptionCnt) {
		logger.error(e.getMessage(), new Throwable(logger.getName() + "_" + exceptionCnt));
	}

	/**
	 * Generate a error-level message.
	 * 
	 * @param s the string to print
	 */
	public void error(String s) {
		logger.error(s);
	}

	/**
	 * Generate a error-level message.
	 * 
	 * @param s the string to print
	 */
	public void error(Exception e) {
		logger.error(StackTraceUtility.convertStackTraceToString(e));
	}

}
