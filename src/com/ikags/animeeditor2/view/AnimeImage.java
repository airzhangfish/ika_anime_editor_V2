package com.ikags.animeeditor2.view;
import javax.swing.*;

import com.ikags.animeeditor2.SDef;
import com.ikags.animeeditor2.info.AnimeInfo;
import com.ikags.animeeditor2.info.FrameInfo;
import com.ikags.animeeditor2.info.ImageModInfo;
import com.ikags.animeeditor2.info.ModInfo;
import com.ikags.animeeditor2.util.DrawUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * Title:ika 动画编辑器 Description: 编辑图片和帧，形成动画，导出后给手机调用 Copyright: airzhangfish Copyright (c) 2007 blog: http://airzhangfish.spaces.live.com Company: Comicfishing author airzhangfish version 0.03a standard last updata 2007-8-23 anime_image 动画编辑界面-右下，显示已经加入动画的帧。
 */

public class AnimeImage extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Thread thread;

	public AnimeImage() {
		// this.setPreferredSize(new Dimension(820, 670));
		thread = new Thread(this);
		thread.start();
		bfimg = new BufferedImage(820, 670, BufferedImage.TYPE_INT_ARGB);
	}

	static BufferedImage bfimg;
	static boolean anime_play = false;
	int reflash = -1;

	public void paint(Graphics g) {
		if (AnimePanel.animelist.getSelectedIndex() != reflash) {
			AnimePanel.useframelist.setSelectedIndex(-1);
			reflash = AnimePanel.animelist.getSelectedIndex();
		}

		Graphics2D g2 = (Graphics2D) g;
		Graphics gg = bfimg.getGraphics();

		gg.setColor(new Color(SDef.BG_R, SDef.BG_G, SDef.BG_B));
		gg.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);

		gg.setColor(new Color(Math.abs(SDef.BG_R + 10), Math.abs(SDef.BG_G + 10), Math.abs(SDef.BG_B + 10)));
		gg.fillRect(SDef.MO_X + 300 - 70, SDef.MO_Y + 160, SDef.MO_W, SDef.MO_H);
		gg.setColor(new Color(SDef.SG_R, SDef.SG_G, SDef.SG_B));
		int maxlength = Math.max(g.getClipBounds().width, g.getClipBounds().height);
		for (int i = 0; i < (maxlength / 16) + 1; i++) {
			gg.drawLine(0, 16 * i, maxlength, 16 * i);
			gg.drawLine(16 * i, 0, 16 * i, maxlength);
		}

		// g2.setColor(color2);
		// g.drawLine(410, 0, 410, 820);
		// g.drawLine(0, 350, 820, 350);

		int animeindex = AnimePanel.animelist.getSelectedIndex();
		int useframeindex = AnimePanel.useframelist.getSelectedIndex();

		if (anime_play == true) {
			if (animeindex < 0) {
				AnimePanel.animelist.setSelectedIndex(0);
				animeindex = 0;
			}

			AnimeInfo aif = SDef.mTotalInfo.mAnimelist.elementAt(animeindex);
			int temp_length = aif.mFrameIDlist.size();
			if (temp_length == 0) {
				anime_play = false;
				return;
			}
			int temp_number = runcount % temp_length;
			AnimePanel.useframelist.setSelectedIndex(temp_number);
		}

		animeindex = AnimePanel.animelist.getSelectedIndex();
		useframeindex = AnimePanel.useframelist.getSelectedIndex();
		if (SDef.mTotalInfo.mBigImagelist.size() > 0 && animeindex >= 0 && useframeindex >= 0) {

			AnimeInfo aif = SDef.mTotalInfo.mAnimelist.elementAt(animeindex);

			if (useframeindex >= aif.mFrameIDlist.size()) {
				return;
			}

			int frameid = aif.mFrameIDlist.elementAt(useframeindex).intValue();
			FrameInfo fi = SDef.mTotalInfo.mFramelist.get(frameid);

			for (int i = 0; i < fi.mModlist.size(); i++) {
				ModInfo mod = fi.mModlist.elementAt(i);
				ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(mod.mImageModID);
				DrawUtil.drawAniPart20(imi.mImage, mod.mX * SDef.frame_size + 300 - 70+SDef.AMO_OFFX, mod.mY * SDef.frame_size + 160+SDef.AMO_OFFY, mod.mRotate, mod.mScale, gg);

			}
		}
		g.drawImage(bfimg, 0, 0, null);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void update_Srceen() {
		repaint();
	}

	static int runcount = 0;
	private boolean isrun = true;

	public void run() {
		while (isrun == true) {
			runcount++;
			if (runcount > 655350) {
				runcount = 0;
			}
			repaint();
			try {
				Thread.sleep(SDef.SYSTEM_DELAY);
			} catch (InterruptedException ex) {}
		}
	}

}
