package com.yahia.forum.utils;

import java.util.Base64;

public final class Utils {

    /**
     * Encodes a byte array to a Base64 encoded string.
     *
     * @param imageBytes The byte array to encode.
     * @return The Base64 encoded string.
     */
    public static String encodeImageToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * Decodes a Base64 encoded string to a byte array.
     *
     * @param base64Image The Base64 encoded string to decode.
     * @return The decoded byte array.
     */
    public static byte[] decodeBase64ToImage(String base64Image) {
        return Base64.getDecoder().decode(base64Image);
    }

}
