package com.ikags.animeeditor2.info;

import java.util.Vector;

public class AnimeInfo{
	public Vector<Integer> mFrameIDlist = new Vector<Integer>();
	public String mInfo = "";// 说明

	public boolean moveFrameUp(int index) {
		if (index > 0 && index < mFrameIDlist.size()) {
			// 触发上移
			try {
				Integer needmoveFrameInfo = mFrameIDlist.get(index);
				mFrameIDlist.remove(index);
				mFrameIDlist.add(index - 1, needmoveFrameInfo);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("AnimeInfo-moveFrameUp out of Array:" + index);
		}
		return false;
	}

	public boolean moveFrameDown(int index) {
		if (index >= 0 && index < mFrameIDlist.size() - 1) {
			// 触发上移
			try {
				Integer needmoveFrameInfo = mFrameIDlist.get(index);
				mFrameIDlist.remove(index);
				mFrameIDlist.add(index + 1, needmoveFrameInfo);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("AnimeInfo-moveFrameDown out of Array:" + index);
		}
		return false;
	}

	public boolean addFrameInfo(Integer info) {
		try {
			mFrameIDlist.add(info);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean removeFrameInfo(int index) {
		try {
			mFrameIDlist.remove(index);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	
	public String getName(){
		StringBuffer sb=new StringBuffer();
		sb.append("ani=");
       for(int i=0;i<mFrameIDlist.size();i++){
    	 int mi= mFrameIDlist.elementAt(i).intValue();
   		sb.append(mi);
		sb.append(",");
       }
		return sb.toString();
	}

}
