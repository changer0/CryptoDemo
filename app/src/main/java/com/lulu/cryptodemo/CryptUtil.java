package com.lulu.cryptodemo;

import java.security.InvalidAlgorithmParameterException;
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
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Lulu on 2016/10/19.
 */

public final class CryptUtil {
    public CryptUtil() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // DES 方式
    ///////////////////////////////////////////////////////////////////////////

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


    ///////////////////////////////////////////////////////////////////////////
    //  AES 方式 1
    ///////////////////////////////////////////////////////////////////////////
    public static byte[] aesEncryptSimple(byte[] data, byte[] key) {
        byte[] ret = null;

        if (data != null && key != null) {
            if (data.length > 0 && key.length == 16) {
                // AES 128bit = 16bytes

                try {
                    Cipher cipher = Cipher.getInstance("AES");
                    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                    ret = cipher.doFinal();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
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
    public static byte[] aesDecryptSimple(byte[] data, byte[] key) {
        byte[] ret = null;

        if (data != null && key != null) {
            if (data.length > 0 && key.length == 16) {
                // AES 128bit = 16bytes
                try {
                    Cipher cipher = Cipher.getInstance("AES");
                    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                    cipher.init(Cipher.DECRYPT_MODE, keySpec);
                    ret = cipher.doFinal();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
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

    ///////////////////////////////////////////////////////////////////////////
    // AES 方式2 使用两套密码
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 使用两套密码的加密, 密码强度更高
     * @param data 数据
     * @param key 第一个密码
     * @param ivData 第二个密码
     * @return byte[]
     */
    public static byte[] aesEncryptWithIv(byte[] data, byte[] key, byte[] ivData) {
        byte[] ret = null;

        if (data != null && key != null && ivData != null) {
            if (data.length > 0 && key.length == 16 && ivData.length == 16) {
                // 使用两套密码的, 算法需要写成 AES/算法模式/填充模式
                try {
                    Cipher cipher = Cipher.getInstance("AEC/CBC/PKCS5Padding");
                    // 准备第一套密码
                    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                    // 准备第二套密码
                    IvParameterSpec ivParameterSpec = new IvParameterSpec(ivData);
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
                    ret = cipher.doFinal();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
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
     * 使用两套密码的解密, 密码强度更高
     * @param data 数据
     * @param key 第一个密码
     * @param ivData 第二个密码
     * @return byte[]
     */
    public static byte[] aesDecryptWithIv(byte[] data, byte[] key, byte[] ivData) {
        byte[] ret = null;

        if (data != null && key != null && ivData != null) {
            if (data.length > 0 && key.length == 16 && ivData.length == 16) {
                // 使用两套密码的, 算法需要写成 AES/算法模式/填充模式
                try {
                    Cipher cipher = Cipher.getInstance("AEC/CBC/PKCS5Padding");
                    // 准备第一套密码
                    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                    // 准备第二套密码
                    IvParameterSpec ivParameterSpec = new IvParameterSpec(ivData);
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
                    ret = cipher.doFinal();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
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
