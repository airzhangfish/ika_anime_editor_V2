package com.ikags.animeeditor2.info;

import java.util.Vector;

import com.ikags.animeeditor2.SDef;

public class FrameInfo {
	public Vector<ModInfo> mModlist= new Vector<ModInfo>();
	public String mInfo="";//说明

	
	
	public boolean moveModUp(int index){
		if(index>0&&index<mModlist.size()){
		//触发上移
			try{
			ModInfo needmoveModInfo=mModlist.get(index);
			mModlist.remove(index);
			mModlist.add(index-1, needmoveModInfo);
			return true;
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
			System.out.println("FrameInfo-moveModUp out of Array:"+index);
		}
		return false;
	}
	
	public boolean moveModDown(int index){
		if(index>=0&&index<mModlist.size()-1){
			//触发上移
				try{
				ModInfo needmoveModInfo=mModlist.get(index);
				mModlist.remove(index);
				mModlist.add(index+1, needmoveModInfo);
				return true;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}else{
				System.out.println("FrameInfo-moveModDown out of Array:"+index);
			}
			return false;
	}
	
	
	public boolean addModInfo(ModInfo info){
				try{
				mModlist.add(info);
				return true;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			return false;
	}
	
	public boolean removeModInfo(int index){
		try{
		mModlist.remove(index);
		return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	return false;
}
	
	
	public String getName(){
		StringBuffer sb=new StringBuffer();
		sb.append("frame=");
       for(int i=0;i<mModlist.size();i++){
    	 ModInfo mi= mModlist.elementAt(i);
   		sb.append(mi.mImageModID);
		sb.append(",");
       }
		return sb.toString();
	}
	
	
}
