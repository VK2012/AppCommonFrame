/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.vk.libs.appcommon.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Names image file as MD5 hash of image URI
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.4.0
 */
public class Md5Utils {

    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 10 + 26; // 10 digits + 26 letters

    public static String generateFileName(String imageUri) {
        byte[] md5 = getMD5(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        return bi.toString(RADIX);
    }

    public static String getMD5String(String imageUri) {
        String ret = null;
        byte[] md5 = getMD5(imageUri.getBytes());
        if (md5 != null && md5.length > 0)
            ret = bytesConvertToHexString(md5);
        return ret;
    }
    private static byte[] getMD5(byte[] data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * 把字节数组转化成字符串返回
     *
     * @param bytes
     * @return
     */
    private static String bytesConvertToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte aByte : bytes) {
            String s = Integer.toHexString(0xff & aByte);
            if (s.length() == 1) {
                sb.append("0" + s);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
