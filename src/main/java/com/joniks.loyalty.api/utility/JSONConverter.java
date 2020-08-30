package com.joniks.lotalty.api.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JSONConverter {

	public static String convertObjectToJson(Object objectToConvert) {
		ObjectWriter ow = new ObjectMapper().writer();
		String jsonizedObject = "";
		try {
			jsonizedObject = ow.writeValueAsString(objectToConvert);
		} catch (JsonProcessingException e2) {
		}
		return jsonizedObject;
	}
}
