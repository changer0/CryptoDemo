package com.lulu.cryptodemo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by Lulu on 2016/10/19.
 */

public final class CryptUtil {
    public CryptUtil() {
    }

    /**
     * DES 加密算法
     * @param data 原始数据
     * @param key  密码, 必须是8个字节
     *
     * @return 经过加密之后的内容
     */
    public static byte[] desEncrypt(byte[] data, byte[] key) {
        byte[] ret = null;
        if (data != null && key != null) {
            if (data.length > 0 && key.length == 8) {
                // step 1: 使用Cipher引擎来初始化 加密, 并且设置密码
                try {
                    Cipher cipher = Cipher.getInstance("DES");
                    // step 1.1: DESKeySpec 用于描述DES的密码
                    DESKeySpec spec = new DESKeySpec(key);
                    //step 1.2: 使用SecretKeyFactory生成key对象
                    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
                    SecretKey sKey = secretKeyFactory.generateSecret(spec);

                    // step 1.3: 初始化 Cipher为加密操作, 并且指定密钥
                    cipher.init(Cipher.ENCRYPT_MODE, sKey);

                    // step 2: 加密数据
                    ret = cipher.doFinal(data);

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }



    /**
     * DES 解密算法
     * @param data 原始数据
     * @param key  密码, 必须是8个字节
     *
     * @return 经过解密之后的内容
     */
    public static byte[] desDecrypt(byte[] data, byte[] key) {
        byte[] ret = null;
        if (data != null && key != null) {
            if (data.length > 0 && key.length == 8) {
                // step 1: 使用Cipher引擎来初始化 解密, 并且设置密码
                try {
                    Cipher cipher = Cipher.getInstance("DES");
                    // step 1.1: DESKeySpec 用于描述DES的密码
                    DESKeySpec spec = new DESKeySpec(key);
                    //step 1.2: 使用SecretKeyFactory生成key对象
                    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
                    SecretKey sKey = secretKeyFactory.generateSecret(spec);

                    // step 1.3: 初始化 Cipher为解密操作, 并且指定密钥
                    cipher.init(Cipher.DECRYPT_MODE, sKey);

                    // step 2: 解密数据
                    ret = cipher.doFinal(data);

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }


}
