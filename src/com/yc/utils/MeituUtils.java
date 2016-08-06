package com.yc.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public class MeituUtils {
	
	 /**
	  * 放大缩小操作
	  * @param zoom
	  * @return
	  */
	private static double rate = 1; 
    public static double onZoom(int zoom) {  
        double drate = 0;  
        if (zoom == 0) {  
            drate = showOriginal();  
        } else if (zoom > 0) {  
            drate = zoomOut();  
        } else if (zoom < 0) {  
            drate = zoomIn();  
        }  
        return drate;  
    }  
	      
    private static double showOriginal() {  
        rate = 1;  
        return rate;  
    }  
      
    private static double zoomIn() {  
        // 销毁缩放图片，重新计算缩放图片  
        rate = rate - 0.1;  
        if (rate < 0.1) {  
            rate = 0.1;  
        }  
        return rate;  
    }  
      
    private static double zoomOut() {  
        // 销毁缩放图片，重新计算缩放图片  
        rate = rate + 0.1;  
        if (rate > 5) {  
            rate = 5;  
        }  
        return rate;  
    }  
    
    
    /**
	 * 向右旋转
	 * @param srcData
	 * @return
	 */
	public static   ImageData rotateRight(ImageData srcData) {
	    int bytesPerPixel = srcData.bytesPerLine / srcData.width; //求出每个像素
	    int destBytesPerLine = srcData.height * bytesPerPixel;
	    byte[] newData = new byte[srcData.data.length];
	    int width = 0, height = 0;
	    for (int srcY = 0; srcY < srcData.height; srcY++) {
	      for (int srcX = 0; srcX < srcData.width; srcX++) {
	    	  int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;
	    	  destX = srcData.height-srcY-1;
	    	  destY = srcX;
	    	  width = srcData.height;
	    	  height = srcData.width;
	    	  destIndex = (destY * destBytesPerLine) + (destX * bytesPerPixel);
	    	  srcIndex = (srcY * srcData.bytesPerLine)  + (srcX * bytesPerPixel);
	    	  System.arraycopy(srcData.data, srcIndex, newData, destIndex, bytesPerPixel);
	      }
	    }
	     return new ImageData(width, height, srcData.depth, srcData.palette, destBytesPerLine, newData);
	}
	
	/**
	 * 向左旋转
	 * @param srcData
	 * @return
	 */
	public static  ImageData rotateLeft(ImageData srcData) {
	    int bytesPerPixel = srcData.bytesPerLine / srcData.width; //求出每个像素的字节
	    int destBytesPerLine = srcData.height * bytesPerPixel;
	    byte[] newData = new byte[srcData.data.length];
	    int width = 0, height = 0;
	    for (int srcY = 0; srcY < srcData.height; srcY++) {
	      for (int srcX = 0; srcX < srcData.width; srcX++) {
	    	  int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;
	    	  destX = srcY;
	    	  destY = srcData.width - srcX - 1;
	    	  width = srcData.height;
	    	  height = srcData.width;
	    	  destIndex = (destY * destBytesPerLine) + (destX * bytesPerPixel);
	    	  srcIndex = (srcY * srcData.bytesPerLine)  + (srcX * bytesPerPixel);
	    	  System.arraycopy(srcData.data, srcIndex, newData, destIndex, bytesPerPixel);
	      }
	    }
	     return new ImageData(width, height, srcData.depth, srcData.palette, destBytesPerLine, newData);
	}
	
	
	//图片透明度
	public static Image TouMing(Image image,int left,int top,int right,int bottom,int alpha){//左上角坐标（left,top）右上角（right,bottom） 透明度alp(0-255)
		if(image==null){
			return null;
		}
		ImageData imageData=image.getImageData();
		int width=imageData.width;
		int height=imageData.height;
		if(left>width ||right>width ||top>height||bottom>height){
			return null;
		}			
		
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				imageData.setAlpha(i, j, 255);
			}
		}
		
		for(int ti=left;ti<right;ti++){
			for(int tj=top;tj<bottom;tj++){
				imageData.setAlpha(ti, tj, alpha);
			}
		}
		image=new Image(null,imageData);
		return image;
	}

	
	public static Image TouMing(ImageData imageData,int left,int top,int right,int bottom,int alpha){//左上角坐标（left,top）右上角（right,bottom） 透明度alp(0-255)
		if(imageData==null){
			return null;
		}
		int width=imageData.width;
		int height=imageData.height;
		if(left>width ||right>width ||top>height||bottom>height){
			return null;
		}			
		
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				imageData.setAlpha(i, j, 255);
			}
		}
		
		for(int ti=left;ti<right;ti++){
			for(int tj=top;tj<bottom;tj++){
				imageData.setAlpha(ti, tj, alpha);
			}
		}
		Image image=new Image(null,imageData);
		return image;
	}
	
	/**
	 * 添加图片水印
	 */
	public static InputStream waterMarkImage(String targePath,String waterMarkPath,int width,int height,int x,int y,float alpha){
		 InputStream is=null;
		 FileInputStream fis=null;
		try {
			File file = new File(targePath); 
			
			Common.srcImage=new ImageData(targePath);   //这行是将原图片保存为静态 方便恢复图片文件
			
            BufferedImage image = ImageIO.read(file);
            int srcWidth = image.getWidth(null);
            int srcHeight = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, srcWidth, srcHeight, null);

            BufferedImage waterImage = ImageIO.read(new File(waterMarkPath));    // 水印文件
            int width_1 = width;
            int height_1 = height;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int widthDiff = srcWidth - width_1;
            int heightDiff = srcHeight - height_1;
            if(x < 0){
                x = widthDiff / 2;
            }else if(x > widthDiff){
                x = widthDiff;
            }
            if(y < 0){
                y = heightDiff / 2;
            }else if(y > heightDiff){
                y = heightDiff;
            }
            g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
            g.dispose();
            ImageIO.write(bufferedImage, "jpg", file);
            fis=new FileInputStream(file);
            is=fis;
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return is;
	}
	
	
	/**
	 * 添加文字水印
	 */
	public static InputStream waterMarkWord(String targePath, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha){
		 InputStream is=null;
		 FileInputStream fis=null;
		try {
			File file = new File(targePath);
			
			Common.srcImage=new ImageData(targePath);   //这行是将原图片保存为静态 方便恢复图片文件
			
			BufferedImage image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int width_1 = fontSize * getLength(pressText);
            int height_1 = fontSize;
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if(x < 0){
                x = widthDiff / 2;
            }else if(x > widthDiff){
                x = widthDiff;
            }
            if(y < 0){
                y = heightDiff / 2;
            }else if(y > heightDiff){
                y = heightDiff;
            }
            g.drawString(pressText, x, y + height_1);
            g.dispose();
            ImageIO.write(bufferedImage, "jpg", file);
            fis=new FileInputStream(file);
            is=fis;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return is;
	}
	
	/**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	  */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }
	
    /**
     * 马赛克
     */
    public static InputStream mosaic(String openUrl, int size)throws Exception{
    	InputStream is=null;
		FileInputStream fis=null;
    	File file = new File(openUrl);
    	
    	Common.srcImage=new ImageData(openUrl);   //这行是将原图片保存为静态 方便恢复图片文件
    	
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " 不是一个图片文件!");
        }
        BufferedImage bi = ImageIO.read(file); // 读取该图片
        BufferedImage spinImage = new BufferedImage(bi.getWidth(),bi.getHeight(), bi.TYPE_INT_RGB);
        if (bi.getWidth() < size || bi.getHeight() < size || size <= 0) { // 马赛克格尺寸太大或太小
        	throw new Exception("马赛克格尺寸太大或太小，请重新输入适合的值");
        }

        int xcount = 0; // 方向绘制个数
        int ycount = 0; // y方向绘制个数
        if (bi.getWidth() % size == 0) {
            xcount = bi.getWidth() / size;
        } else {
            xcount = bi.getWidth() / size + 1;
        }
        if (bi.getHeight() % size == 0) {
            ycount = bi.getHeight() / size;
        } else {
            ycount = bi.getHeight() / size + 1;
        }
        int x = 0;   //坐标
        int y = 0;
        // 绘制马赛克(绘制矩形并填充颜色)
        Graphics gs = spinImage.getGraphics();
        for (int i = 0; i < xcount; i++) {
            for (int j = 0; j < ycount; j++) {
                //马赛克矩形格大小
                 int mwidth = size;
                 int mheight = size;
                 if(i==xcount-1){   //横向最后一个比较特殊，可能不够一个size
                     mwidth = bi.getWidth()-x;
                 }
                 if(j == ycount-1){  //同理
                     mheight =bi.getHeight()-y;
                 }
                 // 矩形颜色取中心像素点RGB值
                int centerX = x;
                int centerY = y;
                if (mwidth % 2 == 0) {
                    centerX += mwidth / 2;
                } else {
                    centerX += (mwidth - 1) / 2;
                }
                if (mheight % 2 == 0) {
                    centerY += mheight / 2;
                } else {
                    centerY += (mheight - 1) / 2;
                }
                Color color = new Color(bi.getRGB(centerX, centerY));
                gs.setColor(color);
                gs.fillRect(x, y, mwidth, mheight);
                y = y + size;// 计算下一个矩形的y坐标
            }
            y = 0;// 还原y坐标
            x = x + size;// 计算x坐标
        }
        gs.dispose();
        ImageIO.write(spinImage, "jpg", file); // 保存图片
        fis=new FileInputStream(file);
        is=fis;
        return is;
    }
    
    public static void getsourceImage(String targePath,Image image ){
    	ImageLoader loader = new ImageLoader();
        ImageData imageData = image.getImageData();
        loader.data = new ImageData[] { imageData };
        String path="D:"+"\\srcImage.jpg";
        loader.save(path, 5);
    	
    	File file = new File(targePath);
    	File file1=new File(path);
    	 try {
			BufferedImage bi = ImageIO.read(file1); // 读取该图片
			 ImageIO.write(bi, "jpg", file); // 保存图片
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
