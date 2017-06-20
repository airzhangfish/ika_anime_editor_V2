package com.ikags.animeeditor2.view;
import javax.swing.*;

import com.ikags.animeeditor2.SDef;
import com.ikags.animeeditor2.info.AnimeInfo;
import com.ikags.animeeditor2.info.FrameInfo;
import com.ikags.animeeditor2.info.ImageModInfo;
import com.ikags.animeeditor2.info.ModInfo;
import com.ikags.animeeditor2.util.DrawUtil;

import java.awt.*;

public class AnimeFrameImage extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Thread thread;

	/**
	 * <p>
	 * Title:ika 动画编辑器
	 * </p>
	 * <p>
	 * Description: 编辑图片和帧，形成动画，导出后给手机调用
	 * </p>
	 * <p>
	 * Copyright: airzhangfish Copyright (c) 2007
	 * </p>
	 * <p>
	 * blog: http://airzhangfish.spaces.live.com
	 * </p>
	 * <p>
	 * Company: Comicfishing
	 * </p>
	 * <p>
	 * author airzhangfish
	 * </p>
	 * <p>
	 * version 0.03a standard
	 * </p>
	 * <p>
	 * last updata 2007-8-23
	 * </p>
	 * anime_frame_image 动画编辑界面-右上，显示选择的帧。
	 */

	public AnimeFrameImage() {
		// this.setPreferredSize(new Dimension(820, 670));
		thread = new Thread(this);
		thread.start();
	}

	int reflash = -1;
	public void paint(Graphics g) {
		try{
		int index = AnimePanel.animelist.getSelectedIndex();
		if (SDef.mTotalInfo.mBigImagelist.size() > 0 && index != reflash) {
			if (index < 0) {
				AnimePanel.animelist.setSelectedIndex(0);
				index = 0;
			}
			AnimeInfo aif = SDef.mTotalInfo.mAnimelist.get(index);
			SDef.use_framelistModel.clear();
			for (int i = 0; i < aif.mFrameIDlist.size(); i++) {
				int frameid = aif.mFrameIDlist.get(i);
				SDef.use_framelistModel.addElement("frame_" + frameid);
			}
			AnimePanel.useframelist.setSelectedIndex(-1);
			reflash = AnimePanel.animelist.getSelectedIndex();
		}
		g.setColor(new Color(SDef.BG_R, SDef.BG_G, SDef.BG_B));
		g.fillRect(0, 0, 820, 670);
		g.setColor(new Color(Math.abs(SDef.BG_R + 10), Math.abs(SDef.BG_G + 10), Math.abs(SDef.BG_B + 10)));
		g.fillRect(SDef.MO_X + 300 - 70, SDef.MO_Y + 160 + 20, SDef.MO_W, SDef.MO_H);
		Graphics2D g2 = (Graphics2D) g;
		// 绘画

		int frameindex = AnimePanel.framelist.getSelectedIndex();
		if (SDef.mTotalInfo.mFramelist.size()>0 && frameindex >= 0) {
			FrameInfo frame = SDef.mTotalInfo.mFramelist.get(frameindex);

			for (int i = 0; i < frame.mModlist.size(); i++) {
				ModInfo mod = frame.mModlist.elementAt(i);
				ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(mod.mImageModID);
				DrawUtil.drawAniPart20(imi.mImage, mod.mX * SDef.frame_size + 300 - 70+SDef.AMO_OFFX, mod.mY * SDef.frame_size + 160 + 20+SDef.AMO_OFFY, mod.mRotate, mod.mScale, g);
			}
		}
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void update_Srceen() {
		repaint();
	}

	private boolean isrun = true;
	public void run() {
		while (isrun == true) {
			repaint();
			try {
				Thread.sleep(SDef.SYSTEM_DELAY);
			} catch (InterruptedException ex) {}
		}
	}

}
