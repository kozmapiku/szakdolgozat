package hu.kozma.backend.mappers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CommonMappers {
	public static byte[] base64ToImage(String base64) {
		return Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
	}

	public static String imageToBase64(byte[] image) {
		return Base64.getEncoder().encodeToString(image);
	}
}
