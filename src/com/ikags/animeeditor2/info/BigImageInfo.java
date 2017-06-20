package com.ikags.animeeditor2.info;

import java.awt.image.BufferedImage;

public class BigImageInfo {
public String mPath=null;  //Í¼Æ¬Â·¾¶¾ø¶ÔµØÖ·
public BufferedImage mBigImage=null; 
public BufferedImage mStaticBigImage=null; 



public String getName(){
	StringBuffer sb=new StringBuffer();
	sb.append("Image=");
	
	try{
		if(mPath!=null){
			int str_find_start = mPath.lastIndexOf("\\");
			int str_find_end = mPath.length();
			sb.append(mPath.substring(str_find_start, str_find_end));
		}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	
	return sb.toString();
}


}
