package com.ikags.animeeditor2.view;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.MouseInputAdapter;

import com.ikags.animeeditor2.SDef;
import com.ikags.animeeditor2.info.BigImageInfo;
import com.ikags.animeeditor2.info.ImageModInfo;

import java.awt.event.MouseEvent;

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
 * frame_mod_image帧编辑界面右上角，物块选取
 * 
 */

public class FrameModImage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Thread thread;

	public FrameModImage() {
		// this.setPreferredSize(new Dimension(820, 670));
		MyListener myListener = new MyListener();
		addMouseListener(myListener);
		addMouseMotionListener(myListener);
	}

	static int pressRect = -1;
	int change_xxx = 0;
	int change_yyy = 0;
	
	int curretpointX=0;
	int curretpointY=0;
	
	class MyListener extends MouseInputAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			curretpointX=e.getX();
			curretpointY=e.getY();
			super.mouseMoved(e);
		}

		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isMiddleMouseButton(e)) {
				if (SDef.mTotalInfo.mBigImagelist.size() < 1) {
					return;
				}
				ModPanel.ImageList.setSelectedIndex((ModPanel.ImageList.getSelectedIndex() + 1) % SDef.mTotalInfo.mBigImagelist.size());

			}
			if (SwingUtilities.isLeftMouseButton(e)) {
				for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(i);
					if (e.getX() > imi.mX * SDef.mod_size + xxx &&
							e.getX() < imi.mX * SDef.mod_size + xxx + imi.mWidth * SDef.mod_size &&
							e.getY() > imi.mY * SDef.mod_size + yyy &&
							e.getY() < imi.mY * SDef.mod_size + yyy + imi.mHeight * SDef.mod_size&&
							imi.mBigImageID == ModPanel.ImageList.getSelectedIndex()) {
						pressRect = i;
						ModPanel.wordList.setSelectedIndex(pressRect);
						break;
					}
				}
				FrameImage.frame_pressRect = -1;
			}

			if (SwingUtilities.isRightMouseButton(e)) {
				change_xxx = e.getX() - xxx;
				change_yyy = e.getY() - yyy;
			}
			update_Srceen();
		}

		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				xxx = e.getX() - change_xxx;
				yyy = e.getY() - change_yyy;

			}
			update_Srceen();
		}

	}

	int xxx = 10;
	int yyy = 10;
	public void paint(Graphics g) {
		g.setColor(new Color(SDef.BG_R, SDef.BG_G, SDef.BG_B));
		g.fillRect(0, 0, 820, 670);
		Graphics2D g2 = (Graphics2D) g;

		// 大图
		if (ModPanel.ImageList.getSelectedIndex() >= 0 && ModPanel.ImageList.getSelectedIndex() >= 0) {
			BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(ModPanel.ImageList.getSelectedIndex());
			g.drawImage(bii.mBigImage, xxx, yyy, null);
		}

		g.setColor(new Color(88, 88, 88));
		for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
			ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(i);
			if (imi.mBigImageID == ModPanel.ImageList.getSelectedIndex()) {
				g.drawRect((int) (imi.mX * SDef.mod_size + xxx), (int) (imi.mY * SDef.mod_size + yyy), (int) (imi.mWidth * SDef.mod_size), (int) (imi.mHeight * SDef.mod_size));
			}
		}
		g.setColor(new Color(188, 188, 188));
		if (pressRect != -1) {

			ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(pressRect);

			if (imi.mBigImageID == ModPanel.ImageList.getSelectedIndex()) {
				g.drawRect((int) (imi.mX * SDef.mod_size + xxx), (int) (imi.mY * SDef.mod_size + yyy), (int) (imi.mWidth * SDef.mod_size), (int) (imi.mHeight * SDef.mod_size));
			}
		}
		g2.setColor(new Color(0, 0, 0));
		g2.drawString(xxx + "," + yyy + " X" + SDef.mod_size+"("+curretpointX+","+curretpointY+")", 50, 10);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		
		
	}

	public void update_Srceen() {
		repaint();
	}

}
