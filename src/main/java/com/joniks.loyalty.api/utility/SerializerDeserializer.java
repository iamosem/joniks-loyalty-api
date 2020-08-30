package com.joniks.loyalty.api.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Random;

import org.apache.axis.encoding.Base64;
import org.apache.log4j.Logger;

import com.joniks.loyalty.api.config.PDFGenerator;

public class SerializerDeserializer {
	final static Logger logger = Logger.getLogger(SerializerDeserializer.class);

	public static final String deserializeObjectFromString(String serializedString) {
		String deserializedObject = null;
		ByteArrayInputStream bi = null;
		ObjectInputStream si = null;

		try {
			byte[] decipher = Base64.decode(serializedString);
			bi = new ByteArrayInputStream(decipher);
			si = new ObjectInputStream(bi);
			String actualSerial = (String) si.readObject();

			String cipher = actualSerial.substring(12);
			byte b[] = Base64.decode(cipher);
			bi = new ByteArrayInputStream(b);
			si = new ObjectInputStream(bi);
			deserializedObject = (String) si.readObject();
		} catch (Exception e) {
			logger.debug(StackTraceUtility.convertStackTraceToString(e));
		} finally {
			if (null != bi) {
				try {
					bi.close();
				} catch (IOException e) {
				}
			}
			if (null != si) {
				try {
					si.close();
				} catch (IOException e) {
				}
			}
		}
		return deserializedObject;
	}

	public final static String serializeObjectToString(Object objectToSerialize) {
		Random rand = new Random((new Date()).getTime());
		String serializedString = "";
		byte[] salt = new byte[8];
		try {
			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream outputStream = new ObjectOutputStream(byteOutputStream);
			outputStream.writeObject(objectToSerialize);
			outputStream.flush();
			rand.nextBytes(salt);
			String tempSerializedString = new String(Base64.encode(salt) + Base64.encode(byteOutputStream.toByteArray()));
			byteOutputStream = new ByteArrayOutputStream();
			outputStream = new ObjectOutputStream(byteOutputStream);
			outputStream.writeObject(tempSerializedString);
			serializedString = new String(Base64.encode(byteOutputStream.toByteArray()));
		} catch (Exception e) {
			logger.debug(StackTraceUtility.convertStackTraceToString(e));
		}
		return serializedString;
	}

	public static void main(String[] args) {
		String ser = serializeObjectToString("CR0722612387");
		System.out.println("SERIALZED STRING = " + ser);
//		PDFGenerator.generateBarcode("C:\\Users\\Michael Encarnacion\\Desktop\\test2.png", ser);
		System.out.println("DESERIALIZED STRING = " + ((String) deserializeObjectFromString("rO0ABXQAKDRDanlhUE1Seit3PXJPMEFCWFFBREVOU01EY3lNall4TWpNNE53PT0=")).toString());
	}

}
