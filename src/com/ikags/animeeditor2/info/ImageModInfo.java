package com.ikags.animeeditor2.info;

import java.awt.image.BufferedImage;

public class ImageModInfo {
	public BufferedImage mImage=null; 
	public BufferedImage mStaticImage=null; 
	public int mX=0;
	public int mY=0;
	public int mWidth=0;
	public int mHeight=0;
	public String mInfo="";//说明
	public int mBigImageID=0;//用了哪张大图 
	public int mUsed=0;//这张小图是否使用 

	
	
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
