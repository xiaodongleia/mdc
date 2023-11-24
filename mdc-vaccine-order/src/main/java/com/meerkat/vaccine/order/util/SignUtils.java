package com.meerkat.vaccine.order.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.StringJoiner;

public class SignUtils {

    private static final BitSet URI_UNRESERVED_CHARACTERS = new BitSet();
    private static final String[] PERCENT_ENCODED_STRINGS = new String[256];
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        for (int i = '0'; i <= '9'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        URI_UNRESERVED_CHARACTERS.set('-');
        URI_UNRESERVED_CHARACTERS.set('.');
        URI_UNRESERVED_CHARACTERS.set('_');
        URI_UNRESERVED_CHARACTERS.set('~');

        for (int i = 0; i < PERCENT_ENCODED_STRINGS.length; ++i) {
            PERCENT_ENCODED_STRINGS[i] = String.format("%%%02X", i);
        }
    }

    public static String join(StringJoiner joiner, Iterable<String> iterable) {
        iterable.forEach(joiner::add);
        return joiner.toString();
    }

    public static String normalizePath(String path) {
        return normalize(path).replace("%2F", "/");
    }

    public static String normalize(String value) {
        StringBuilder builder = new StringBuilder();
        for (byte b : value.getBytes(StandardCharsets.UTF_8)) {
            if (URI_UNRESERVED_CHARACTERS.get(b & 0xFF)) {
                builder.append((char) b);
            } else {
                builder.append(PERCENT_ENCODED_STRINGS[b & 0xFF]);
            }
        }
        return builder.toString();
    }

    public static String sha256Hex(String signingKey, String stringToSign) {
        try {
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(new SecretKeySpec(signingKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return bytesToHex(instance.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate signature.", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}