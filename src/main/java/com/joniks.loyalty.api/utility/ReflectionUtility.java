package com.joniks.lotalty.api.utility;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.util.StringUtils;

import com.joniks.lotalty.api.entity.User;

public class ReflectionUtility {

	public static Field[] getMemberVariables(Object obj) {
		Class<?> objectClass = obj.getClass();
		return objectClass.getDeclaredFields();
	}

	public static Object getValue(Object obj, String memberVariableName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> objectClass = obj.getClass();
		Method method = objectClass.getMethod("get" + StringUtils.capitalize(memberVariableName));
		return method.invoke(obj);
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		User customer = new User();
		customer.setEmail("HOHO PHONE");
		customer.setFirstName("HOHO NAME");
		Field[] fields = getMemberVariables(customer);
		// Method method;
		for (Field field : fields) {
			// method = Customer.class.getMethod("getPhone");
			String name = field.getName();

			Class<?> type = field.getType();
			System.out.println("NAME AND TYPE = " + name + " - " + type + " - " + getValue(customer, name));
		}
	}
}
