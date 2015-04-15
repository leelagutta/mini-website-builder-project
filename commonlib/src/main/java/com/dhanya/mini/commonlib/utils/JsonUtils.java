package com.dhanya.mini.commonlib.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.dhanya.mini.commonlib.exception.JsonException;

/**
 * Helper class that converts string to Json and from Json to any generic type
 * 
 * @author Leela
 */
public class JsonUtils {

	private static ObjectMapper mapper = new ObjectMapper();
	
	private static final Logger log = Logger.getLogger(JsonUtils.class.getName());
	
	public static String toJson(Object obj) {		
		try {
		 	return mapper.writeValueAsString(obj);
		
		} catch (JsonGenerationException e) {
			 log.info(e);
			throw new JsonException("json exception");
		} catch (JsonMappingException e) {
			log.info(e);
			throw new JsonException("json exception");
		} catch (IOException e) {
			log.info(e);
			throw new JsonException("json exception");
		}
	}
	
	public static <T> T fromJson(Object fromValue, Class<T> klassType) {
		return mapper.convertValue(fromValue, klassType);
	}
	
}