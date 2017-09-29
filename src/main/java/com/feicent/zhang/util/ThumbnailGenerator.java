package com.feicent.zhang.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 生成缩略图类
 */
public class ThumbnailGenerator {
	
	public static void main(String[] args) throws Exception {
		String thumbnailFile = "D:/yzuzhang/IPv4_min.png";
		compress("D:/yzuzhang/IPv4.png", thumbnailFile, 200, 215);
		System.out.println("生成缩略图："+thumbnailFile);
	}
	
	/**
	 * compress
	 * @param originalFile	要压缩的图片
	 * @param thumbnailFile	压缩后的图片
	 * @param thumbWidth	压缩宽度大小
	 * @param thumbHeight	压缩高度大小		
	 * @throws Exception
	 */
	public static void compress(String originalFile, String thumbnailFile, int thumbWidth, int thumbHeight) throws Exception 
	{
		Image image = ImageIO.read(new File(originalFile));
	    // 压缩比例
	    double thumbRatio = (double)thumbWidth / (double)thumbHeight;
	    int imageWidth    = image.getWidth(null);
	    int imageHeight   = image.getHeight(null);
	    double imageRatio = (double)imageWidth / (double)imageHeight;
	    
	    if (thumbRatio < imageRatio) {
	    	thumbHeight = (int)(thumbWidth / imageRatio);
	    } else {
	      	thumbWidth = (int)(thumbHeight * imageRatio);
	    }
	    
		if(imageWidth < thumbWidth && imageHeight < thumbHeight) {
			thumbWidth = imageWidth;
			thumbHeight = imageHeight;
		} else if(imageWidth < thumbWidth)
			thumbWidth = imageWidth;
		  else if(imageHeight < thumbHeight)
			thumbHeight = imageHeight;
		
	    BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = thumbImage.createGraphics();
	    graphics2D.setBackground(Color.WHITE);
    	graphics2D.setPaint(Color.WHITE); 
    	graphics2D.fillRect(0, 0, thumbWidth, thumbHeight);
	    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
	    
		ImageIO.write(thumbImage, "JPG", new File(thumbnailFile));
	}
}
