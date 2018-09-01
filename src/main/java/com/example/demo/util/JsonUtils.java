package com.example.demo.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonUtils {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static final String INCLUDE = "JSON_INCLUDE";
	private static final String EXCEPT = "JSON_EXCEPT";

	@com.fasterxml.jackson.annotation.JsonFilter(INCLUDE)
	interface MyJsonInclude {
	}

	@com.fasterxml.jackson.annotation.JsonFilter(EXCEPT)
	interface MyJsonExcept {
	}

	private  static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static <T> T serializable(String json, Class<T> clazz) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static <T> T serializable(String json, TypeReference reference) {

		if (StringUtils.isEmpty(json)) {
			return null;
		}
		try {
			return objectMapper.readValue(json, reference);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return  null;
		}

	}

	public static String deserializer(Object json) {
		if (json == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	public static String toJson(Object dest) {
		return toJson(dest, "yyyy-MM-dd HH:mm:ss");
	}

	public static String toJson(Object dest, String dateFormat) {
		if (dest == null) {
			return null;
		}
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		if (StringUtils.hasText(dateFormat)) {
			objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
		}
		try {
			String value = objectMapper.writeValueAsString(dest);
			return value;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T jsonToObject(String jsonContent, Class<T> classT) {
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try {
			T object = objectMapper.readValue(jsonContent, classT);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> jsonToList(String jsonContent, Class<T> classT) {
		if (StringUtils.isEmpty(jsonContent)) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, classT);
		try {
			return objectMapper.readValue(jsonContent, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <K, V> HashMap<K, V> jsonToMap(String jsonContent, Class<?> classT) {
		if (StringUtils.isEmpty(jsonContent)) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try {
			HashMap<K, V> object = (HashMap<K, V>) objectMapper.readValue(jsonContent, classT);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static <K, V> LinkedHashMap<K, V> jsonToLinkedMap(String jsonContent, Class<?> classT) {
		if (StringUtils.isEmpty(jsonContent)) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		try {
			LinkedHashMap<K, V> object = (LinkedHashMap<K, V>) objectMapper.readValue(jsonContent, classT);
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String toJsonInclude(Object dest, String... includeFields) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		try {
			if (includeFields != null && includeFields.length > 0) {
				objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(INCLUDE, SimpleBeanPropertyFilter.filterOutAllExcept(includeFields)));
				objectMapper.addMixIn(dest.getClass(), MyJsonInclude.class);
			}

			return objectMapper.writeValueAsString(dest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toJsonExclude(Object dest, String... excludeFields) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		try {
			if (excludeFields != null && excludeFields.length > 0) {
				objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(EXCEPT, SimpleBeanPropertyFilter.serializeAllExcept(excludeFields)));

				objectMapper.addMixIn(dest.getClass(), MyJsonExcept.class);
			}

			return objectMapper.writeValueAsString(dest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
