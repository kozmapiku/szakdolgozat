package hu.kozma.backend.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RestResponseHandler {
	public static ResponseEntity<Object> generateResponse(Object data) {
		Map<String, Object> map = generateBaseResponse();
		map.put("data", data);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	public static ResponseEntity<Object> generateResponse(String message) {
		Map<String, Object> map = generateBaseResponse();
		map.put("data", message);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	private static Map<String, Object> generateBaseResponse() {
		Map<String, Object> map = new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.OK);
		return map;
	}
}
