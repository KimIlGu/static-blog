package com.kig.java.blog.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

	public static String getNowDateStr() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = date.format(cal.getTime());
		return dateStr;
	}

	public static void makeDir(String dirPath) {
		File file = new File(dirPath);
		if(file.exists() == false) {
			file.mkdir();
		}	
	}

	public static String lcFirst(String str) {
		String newStr = "";
		newStr += str.charAt(0);
		newStr = newStr.toLowerCase();
		
		return newStr + str.substring(1);
	}

	public static boolean isFileExists(String filePath) {
		File file = new File(filePath);
		
		if (file.isFile()) {
			return true;
		}
		return false;
	}

	public static void writeFileContents(String filePath, int data) {
		writeFileContents(filePath, data + "");
	}

	public static void writeFileContents(String filePath, String contents) {
		BufferedOutputStream bs = null;
		try {
			bs = new BufferedOutputStream(new FileOutputStream(filePath));
			bs.write(contents.getBytes()); 
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				bs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getFileContents(String filePath) {
		String rs = null;
		try {
			FileInputStream fileStream = null;
			
			fileStream = new FileInputStream(filePath);
			
			byte[] readBuffer = new byte[fileStream.available()];
			
			while (fileStream.read(readBuffer) != -1) {
				
			}
			
			rs = new String(readBuffer);
			
			fileStream.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return rs;
	}

	public static void writeJsonFile(String filePath, Object obj) {
		ObjectMapper om = new ObjectMapper();
		
		try {
			om.writeValue(new File(filePath), obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object getObjectFromJson(String filePath, Class cls) {
		ObjectMapper om = new ObjectMapper();
		
		Object obj = null;
		try {
			obj = om.readValue(new File(filePath), cls);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

}