package com.joniks.lotalty.api.logger;

import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class SQLLogger implements MessageFormattingStrategy {

	private final Formatter formatter = new BasicFormatterImpl();

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
		return "SQL: " + sql;
	}
}