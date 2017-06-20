package com.ikags.animeeditor2.info;

import java.awt.image.BufferedImage;

public class ImageModInfo {
	public BufferedImage mImage=null; 
	public BufferedImage mStaticImage=null; 
	public int mX=0;
	public int mY=0;
	public int mWidth=0;
	public int mHeight=0;
	public String mInfo="";//˵��
	public int mBigImageID=0;//�������Ŵ�ͼ 
	public int mUsed=0;//����Сͼ�Ƿ�ʹ�� 

	
	
	public String getName(){
		StringBuffer sb=new StringBuffer();
		sb.append("imageMod=");
		sb.append(mX);
		sb.append(",");
		sb.append(mY);
		sb.append("(");
		sb.append(mWidth);
		sb.append("x");
		sb.append(mHeight);
		sb.append(")");
		sb.append(mInfo);
		return sb.toString();
	}
	
	
}
