package com.feicent.zhang.util.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 生成二维码工具类
 */
public class QRCodeUtil {

	public static void main(String[] args) {
		// 二维码生成路径
		String filePath = "D:/feicent.jpg";
		// 二维码跳转内容
		String contet = "http://izhongwei.github.io/blog/index.html";
		contet = "www.feicent.cn";
		creteCode(contet, filePath);
	}
	
	public static void creteCode(String contet, String filePath) {
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(contet,
					BarcodeFormat.QR_CODE, 400, 400, hints);// 生成二维码尺寸大小

			File file = new File(filePath);// 生成二维码后的图片路径和名称
			ImageWrite.writeToFile(bitMatrix, "jpg", file);// 需要ImageWrite类生成图片
			System.out.println("二维码已经生成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
