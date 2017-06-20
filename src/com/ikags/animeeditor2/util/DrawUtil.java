package com.ikags.animeeditor2.util;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.ikags.animeeditor2.SDef;

public class DrawUtil {

	
	// ��ת
	public static AffineTransform transform;
	public static AffineTransformOp op;
	public static BufferedImage filteredImage;

	private static BufferedImage rotate90(BufferedImage img, int angle,float scale) {
		
		//���ƴ�С��Ϊ�漰��ת,ֱ�Ӱ��նԽ��߳���*scale����,Ȼ�����е���ת����
		double ma=img.getWidth()*img.getWidth()+img.getHeight()*img.getHeight();
		int imgMaxwidth=(int)(Math.sqrt(ma)*scale);
		float imgwidth=img.getWidth()*scale;
		float imgHeight=img.getHeight()*scale;
			transform = AffineTransform.getRotateInstance(Math.toRadians(angle), imgMaxwidth/2, imgMaxwidth/2);
			transform.scale(scale, scale);
			op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			filteredImage = new BufferedImage(imgMaxwidth,imgMaxwidth, img.getType());
			op.filter(img, filteredImage);
		return filteredImage;
	}

	// �滭����
	public static void drawAniPart20(BufferedImage bfimage, int x, int y, int trans,float scale, Graphics g) {
	      if(bfimage==null){
    	   System.out.println("drawAniPart20 image  null");
	    	  return;
       }
			rotate90(bfimage, trans,scale);
			g.drawImage(filteredImage, x, y, null);
	
	}

	public static BufferedImage getbigimage(BufferedImage bfimage){
				BufferedImage temp_bfimage = null;
		try{
		temp_bfimage = new BufferedImage((int) (bfimage.getWidth() * SDef.frame_size),
				(int) (bfimage.getHeight() * SDef.frame_size),
				BufferedImage.TYPE_INT_ARGB);
          temp_bfimage.getGraphics().drawImage(bfimage, 0, 0, temp_bfimage.getWidth(), temp_bfimage.getHeight(), null); 
		}catch(Exception e){
			System.out.println("getbigimage error");
		}
		return  temp_bfimage;
	}
	
	
	
	// �滭����
//	public static void drawAniPart20_back(BufferedImage bfimage, int x, int y, int trans, Graphics g) {
//	      if(bfimage==null){
//    	   System.out.println("drawAniPart20 image  null");
//	    	  return;
//       }	
//		if (trans == 0) {
//			rotate90(bfimage, 0);
//			g.drawImage(filteredImage, x, y, null);
//		} else {
//			switch (trans) {
//			case 1: // X����
//				rotate90(bfimage, 0);
//				g.drawImage(filteredImage, x - filteredImage.getWidth(), y, x, y + filteredImage.getHeight(), filteredImage.getWidth(), 0, 0,
//						filteredImage.getHeight(), null); // X����
//				break;
//			case 2: // Y����
//				rotate90(bfimage, 0);
//				g.drawImage(filteredImage, x, y - filteredImage.getHeight(), x + filteredImage.getWidth(), y, 0, filteredImage.getHeight(),
//						filteredImage.getWidth(), 0, null); // Y����
//				break;
//			case 3: // X����+Y���� ������ת180��
//				rotate90(bfimage, 180);
//				g.drawImage(filteredImage, x - filteredImage.getWidth(), y - filteredImage.getHeight(), null);
//				break;
//			case 4: // ��ת270+Y����
//				rotate90(bfimage, 270);
//				g.drawImage(filteredImage, x, y, x + filteredImage.getWidth(), y + filteredImage.getHeight(), filteredImage.getWidth(), 0, 0,
//						filteredImage.getHeight(), null); // Y����
//				break;
//			case 5: // ��ת90
//				rotate90(bfimage, 90);
//				g.drawImage(filteredImage, x, y, null);
//				break;
//			case 6: // ��ת270
//				rotate90(bfimage, 270);
//				g.drawImage(filteredImage, x, y, null);
//				break;
//			case 7: // ��ת90+Y����
//				rotate90(bfimage, 90);
//				g.drawImage(filteredImage, x, y, x + filteredImage.getWidth(), y + filteredImage.getHeight(), filteredImage.getWidth(), 0, 0,
//						filteredImage.getHeight(), null); // Y����
//				break;
//			default:
//				System.out.println("2.0trans error");
//				break;
//			}
//		}
//	}
	
}
