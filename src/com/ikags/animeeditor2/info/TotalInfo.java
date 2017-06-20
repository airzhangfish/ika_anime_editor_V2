package com.ikags.animeeditor2.info;

import java.util.Vector;

import com.ikags.animeeditor2.SDef;

public class TotalInfo {
	public Vector<BigImageInfo> mBigImagelist = new Vector<BigImageInfo>();
	public Vector<ImageModInfo> mImageModlist = new Vector<ImageModInfo>();

	public Vector<FrameInfo> mFramelist = new Vector<FrameInfo>();
	public Vector<AnimeInfo> mAnimelist = new Vector<AnimeInfo>();

	/**
	 * ���Ӵ�ͼģ��
	 * @param info
	 * @return
	 */
	public boolean addBigImageInfo(BigImageInfo info) {
		try {
			mBigImagelist.add(info);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
/**
 * ɾ����ͼģ��
 * @param index
 * @return
 */
	public boolean removeBigImageInfo(int index) {
		try {
			mBigImagelist.remove(index);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
/**
 * �������ͼģ��
 * @param info
 * @return
 */
	public boolean addImageModInfo(ImageModInfo info) {
		try {
			mImageModlist.add(info);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

/**
 * ɾ�����ͼģ��
 * @param index
 * @return
 */
	public boolean removeImageModInfo(int index) {
		try {
			mImageModlist.remove(index);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	
/**
 * ֡�����ƶ�
 * @param index
 * @return
 */
	public boolean moveFrameUp(int index) {
		if (index > 0 && index < mFramelist.size()) {
			// ��������
			try {
				FrameInfo needmoveFrameInfo = mFramelist.get(index);
				mFramelist.remove(index);
				mFramelist.add(index - 1, needmoveFrameInfo);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("TotalInfo-moveFrameUp out of Array:" + index);
		}
		return false;
	}

	
	/**
	 * ֡�����ƶ�
	 * @param index
	 * @return
	 */
	public boolean moveFrameDown(int index) {
		if (index >= 0 && index < mFramelist.size() - 1) {
			// ��������
			try {
				FrameInfo needmoveFrameInfo = mFramelist.get(index);
				mFramelist.remove(index);
				mFramelist.add(index + 1, needmoveFrameInfo);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("TotalInfo-moveFrameDown out of Array:" + index);
		}
		return false;
	}
/**
 *  ����֡��Ϣ
 * @param info
 * @return
 */
	public boolean addFrameInfo(FrameInfo info) {
		try {
			mFramelist.add(info);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
/**
 * ɾ��֡��Ϣ
 * @param index
 * @return
 */
	public boolean removeFrameInfo(int index) {
		try {
			mFramelist.remove(index);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
/**
 * ����ģ�������ƶ�
 * @param index
 * @return
 */
	public boolean moveAnimeUp(int index) {
		if (index > 0 && index < mAnimelist.size()) {
			// ��������
			try {
				AnimeInfo needmoveAnimeInfo = mAnimelist.get(index);
				mAnimelist.remove(index);
				mAnimelist.add(index - 1, needmoveAnimeInfo);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("TotalInfo-moveFrameUp out of Array:" + index);
		}
		return false;
	}
/**
 * ����ģ�������ƶ�
 * @param index
 * @return
 */
	public boolean moveAnimeDown(int index) {
		if (index >= 0 && index < mAnimelist.size() - 1) {
			// ��������
			try {
				AnimeInfo needmoveAnimeInfo = mAnimelist.get(index);
				mAnimelist.remove(index);
				mAnimelist.add(index + 1, needmoveAnimeInfo);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("TotalInfo-moveFrameDown out of Array:" + index);
		}
		return false;
	}

	/**
	 * ���Ӷ�����Ϣ
	 * @param info
	 * @return
	 */
	public boolean addAnimeInfo(AnimeInfo info) {
		try {
			mAnimelist.add(info);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
/**
 * ɾ��������Ϣ
 * @param index
 * @return
 */
	public boolean removeAnimeInfo(int index) {
		try {
			mAnimelist.remove(index);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * �����ж�������ɾ��ĳһ֡
	 * @param frameID
	 * @return
	 */
	public boolean removeFrameFromAnimeList(int frameID) {
		for (int i = 0; i < mAnimelist.size(); i++) {
			AnimeInfo ai = mAnimelist.elementAt(i);
			for (int k = 0; k < ai.mFrameIDlist.size(); k++) {
				int myint = ai.mFrameIDlist.elementAt(k).intValue();
				if (myint > frameID) {
					myint--;
					ai.mFrameIDlist.remove(k);
					ai.mFrameIDlist.add(k, myint);
				}
			}
		}
		return true;
	}
	
	
	/**
	 * �ж�֡�����Ƿ���mod����
	 * @param imagemod
	 * @return
	 */
		public String hasFramefromAnimelist(int imagemod){
			String usedframe="";
		  	  for(int i=0;i<mAnimelist.size();i++){
		  		AnimeInfo fi= mAnimelist.elementAt(i);
				  for(int k=0;k<fi.mFrameIDlist.size();k++){
					  Integer mi=fi.mFrameIDlist.elementAt(k);
				  if(imagemod==mi){
					  usedframe=usedframe+i+",";
					  }
				  }
			  }
				return usedframe;
			}
			
	
		/**
		 * ĳһ֡����,�����ж����еĴ�֡��Ҫ����
		 * @return
		 */
		public boolean moveFrameUpFromAnime(int frameID){
			
			for (int i = 0; i < mAnimelist.size(); i++) {
				AnimeInfo ai = mAnimelist.elementAt(i);
				for (int k = 0; k < ai.mFrameIDlist.size(); k++) {
					int myint = ai.mFrameIDlist.elementAt(k).intValue();
					if (myint == frameID) {
						myint=frameID-1;
						ai.mFrameIDlist.remove(k);
						ai.mFrameIDlist.add(k, myint);
					}else if(myint == frameID-1){
						myint=frameID;
						ai.mFrameIDlist.remove(k);
						ai.mFrameIDlist.add(k, myint);
					}
				}
			}
			return true;
		}
		
		/**
		 * ĳһ֡����,�����ж����еĴ�֡��Ҫ����
		 * @return
		 */
		public boolean moveFrameDownFromAnime(int frameID){
			
			for (int i = 0; i < mAnimelist.size(); i++) {
				AnimeInfo ai = mAnimelist.elementAt(i);
				for (int k = 0; k < ai.mFrameIDlist.size(); k++) {
					int myint = ai.mFrameIDlist.elementAt(k).intValue();
					if (myint == frameID) {
						myint=frameID+1;
						ai.mFrameIDlist.remove(k);
						ai.mFrameIDlist.add(k, myint);
					}else if(myint == frameID+1){
						myint=frameID;
						ai.mFrameIDlist.remove(k);
						ai.mFrameIDlist.add(k, myint);
					}
				}
			}
			
			return true;
		}
		
	
	/**
	 * ������֡����ɾ��ĳһ��ID��ͼƬ,ͼƬλ���ƶ�
	 * @param modID
	 * @return
	 */
	public boolean removeImageModFromFrameList(int modID) {
     	  for(int i=0;i<mFramelist.size();i++){
    		  FrameInfo fi= mFramelist.elementAt(i);
    		  for(int k=0;k<fi.mModlist.size();k++){
    			  ModInfo mi=fi.mModlist.elementAt(k);
    		  if(mi.mImageModID>modID){
    			  mi.mImageModID--;
    			  }
    		  }
    	  }
		return true;
	}
	
/**
 * �ж�֡�����Ƿ���mod����
 * @param imagemod
 * @return
 */
	public String hasModIDfromFramelist(int imagemod){
		String usedframe="";
	  	  for(int i=0;i<mFramelist.size();i++){
			  FrameInfo fi= mFramelist.elementAt(i);
			  for(int k=0;k<fi.mModlist.size();k++){
				  ModInfo mi=fi.mModlist.elementAt(k);
			  if(imagemod==mi.mImageModID){
				  usedframe=usedframe+i+",";
				  }
			  }
		  }
			return usedframe;
		}
		
	

}
