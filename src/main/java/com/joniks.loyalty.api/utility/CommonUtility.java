package com.joniks.loyalty.api.utility;

import java.util.Collection;
import java.util.UUID;

public class CommonUtility {

	public static boolean isObjectNull(Object obj) {
		return (null == obj);
	}

	public static boolean isListNullOrEmpty(Collection list) {
		if (list == null || list.isEmpty())
			return true;
		return false;
	}

	public static String generateUUID(String key) {
		byte[] bytes;
		String uuid = "";
		try {
			bytes = key.getBytes("UTF-8");
			UUID generatedUUID = UUID.nameUUIDFromBytes(bytes);
			uuid = generatedUUID.toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return uuid;
	}

}
