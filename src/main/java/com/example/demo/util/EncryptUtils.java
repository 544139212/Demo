package com.example.demo.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtils {
    protected static final Logger logger = LoggerFactory.getLogger(EncryptUtils.class);

    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    private static final String UTF8 = "utf-8";

    public static String md5(String SourceString) {
        String result = null;
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(SourceString.getBytes(UTF8));
            byte messageDigest[] = digest.digest();
            result = toHexString(messageDigest);
        } catch (Exception e) {
            logger.error("MD5转码失败！", e);
        }

        return result;
    }

    public static String md516(String SourceString) {
        String result = md5(SourceString);
        if (result != null) {
            result = result.substring(8, 24);
        } else {
            result = SourceString;
        }

        return result;
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /*public String base64Encoder(String src) throws UnsupportedEncodingException {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(src.getBytes(UTF8));
    }

    public String base64Decoder(String dest)
            throws NoSuchAlgorithmException, IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return new String(decoder.decodeBuffer(dest), UTF8);
    }*/
}
