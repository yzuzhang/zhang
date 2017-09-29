package com.feicent.zhang.io;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.feicent.zhang.util.CloseUtil;

/**
 * 生成验证码
 * @author yzuzhang
 * @date 2017年1月18日
 */
public class NumImgCode {
	
	public static void main(String[] args) {
		//create();
		//createENCode();
		createCNCode();
	}
	
	public static void createCNCode() {
		OutputStream os = null;
		int width = 106, height = 30;// 设置图片的长宽
		String file = "D:/yzuzhang/createCNCode.jpg";
		try {
			//设置备选汉字，剔除一些不雅的汉字
			String base = "\u6211\u662f\u5f90\u5f20\u660e\u71d5\u534e\u541b\u5c24\u6731\u7ea2\u7231\u4f20\u534e\u6768\u5510\u536b\u5b8f\u950b\u5f20\u4e2d\u56fd\u5317\u4eac\u4e0a\u6d77\u5929\u6d25\u6e56\u5317";
			//备选汉字的长度
			int length = base.length();
			//创建内存图像
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			Graphics g = image.getGraphics();
			//创建随机类的实例
			Random random = new Random();
			// 设定图像背景色(因为是做背景，所以偏淡)
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);
			//备选字体
			String[] fontTypes = { "u5b8bu4f53", "u65b0u5b8bu4f53",
					"u9ed1u4f53", "u6977u4f53", "u96b6u4e66" };
			int fontTypesLength = fontTypes.length;
			//在图片背景上增加噪点
			g.setColor(getRandColor(160, 200));
			g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			for (int i = 0; i < 6; i++) {
				g.drawString("*********************************************",0, 5 * (i + 2));
			}
			//取随机产生的认证码(6个汉字)
			//保存生成的汉字字符串
			String sRand = "";
			for (int i = 0; i < 3; i++) {
				int start = random.nextInt(length);
				String rand = base.substring(start, start + 1);
				sRand += rand;
				//设置字体的颜色
				g.setColor(getRandColor(10, 150));
				//设置字体
				g.setFont(new Font(fontTypes[random.nextInt(fontTypesLength)],
						Font.BOLD, 18 + random.nextInt(6)));
				//将此汉字画到图片上
				g.drawString(rand, 24 * i + 10 + random.nextInt(8), 24);
			}
			//将认证码存入session
			System.out.println( sRand);
			g.dispose();
			
			//输出图象到页面
			os = new FileOutputStream(file, false);
			ImageIO.write(image, "JPEG", os);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			CloseUtil.close(os);
		}
	}
	
	public static void createENCode() {
		OutputStream os = null;
		int width = 110, height = 20;
		String file = "D:/yzuzhang/createENCode.jpg";
		try {
			
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			Random random = new Random();
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);
			
			g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			String[] s = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
					"J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U",
					"V", "W", "X", "Y", "Z" };
			
			String sRand = "";
			for (int i = 0; i < 4; i++) {
				String rand = "";
				if (random.nextBoolean()) {
					rand = String.valueOf(random.nextInt(10));
				} else {
					int index = random.nextInt(25);
					rand = s[index];
				}
				sRand += rand;
				g.setColor(new Color(20 + random.nextInt(10), 20 + random
						.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(rand, 17 * i + 6, 16);
			}
			g.dispose();
			System.out.println(sRand);
			
			os = new FileOutputStream(file, false);
			ImageIO.write(image, "JPEG", os);
			os.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			CloseUtil.close(os);
		}
	}

	public static void create() {
		OutputStream os = null;
		int width = 110, height = 20;
		String file = "D:/yzuzhang/NumImgCode.jpg";
		
		try {
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			Random random = new Random();
			//设置背景颜色
			g.setColor(new Color(251,244,166));
			//填充指定的矩形。
			g.fillRect(0, 0, width, height);
			//设置文字的样式
			g.setFont(new Font("Times New Roman", Font.BOLD, 18));
			//设置文字的颜色
			g.setColor(new Color(198,39,60));
			//设置运算符号
			String[] s = { "+", "-" };
			String sRand = "";
			//设置运算因子
			int num1 = random.nextInt(100);
			int num2 = random.nextInt(100);
			int index = random.nextInt(2);
			String rand = s[index];
			//设置运算结果
			int end = 0;
			//得到运算表达式
			sRand = num1 + rand + num2;
			//绘制运算表达式
			g.drawString(sRand, 13, 16);
			if (rand.equals("+")) {
				end = num1 + num2;
			} else {
				end = num1 - num2;
			}
			System.out.println("end===="+end);
			g.dispose();
			
			os = new FileOutputStream(file, false);
			ImageIO.write(image, "JPEG", os);
			os.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			CloseUtil.close(os);
		}
	}
	
	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
