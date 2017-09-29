package com.feicent.zhang.util.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <p>标题： 对称加密解密AES</p>
 * <p>功能： 对数据进行AES加密解密</p>
 * http://www.cnblogs.com/ikcai/p/7509077.html
 * @date 2017年9月12日
 */
@SuppressWarnings("restriction")
public class EncryAESUtil {
	
	/**
	 * 初始化向量参数，AES 为16bytes 此常量在作为公钥之后不要再改动了
	 */
    private final static String ivParameter    = "f80937b36491699b";

    /**
     * 加密  
     * @param sSrc 需加密数据
     * @param sKey 私钥 ，可以用字母和数字组成,AES固定格式为128/192/256 bits.即：16/24/32bytes。此处使用AES-128-CBC加密模式
     * @return
     * ZhaoLi
     */
    public static String encrypt(String sSrc, String sKey) {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//算法/模式/填充
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e)
        {
            throw new RuntimeException("加密失败！", e);
        }
    }

    /**
     * 解密  
     * @param sSrc 需解密数据
     * @param sKey 私钥 ，可以用字母和数字组成,AES固定格式为128/192/256 bits.即：16/24/32bytes。此处使用AES-128-CBC加密模式
     * @return
     * ZhaoLi
     */
    public static String decrypt(String sSrc, String sKey) {
        try
        {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密  
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception e)
        {
            throw new RuntimeException("解密失败！", e);
        }
    }
    
    public static void main(String[] args) {
        String sKey = "e772834b14c16549";//私钥 注意一定要16位
        String pwd = "limx_1234";
        
        System.out.println("加密前：" + pwd);
        String enData = encrypt(pwd, sKey);
        System.out.println("加密后：" + enData);
        
        String deData = decrypt(enData, sKey);
        System.out.println("解密后:" + deData);
    }
    
}