package com.ikags.animeeditor2.info;

import com.ikags.animeeditor2.SDef;

public class ModInfo{
public int mX=0;
public int mY=0;
public int mRotateType=0; // J2ME��8������
public int mRotate=0; // android/ios/pc����
public float mScale=1; // android/ios/pc����
public String mInfo="";//˵��
public int mImageModID=0;//��������Сͼ 


public String getName(){
	StringBuffer sb=new StringBuffer();
	sb.append("mod=");
	sb.append(mX);
	sb.append("x");
	sb.append(mY);
	sb.append("(");
	sb.append(mRotate);
	sb.append(",");
	sb.append(mScale);
	sb.append(")");
	try{
			ImageModInfo imagemodinfo=SDef.mTotalInfo.mImageModlist.elementAt(mImageModID);
			mInfo=imagemodinfo.mInfo;
	sb.append(""+mInfo);
	}catch(Exception ex){
		ex.printStackTrace();
	}
	return sb.toString();
}

}
