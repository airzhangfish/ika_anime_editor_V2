package com.ikags.animeeditor2.view;
import javax.swing.*;

import java.awt.*;

import javax.swing.event.MouseInputAdapter;

import com.ikags.animeeditor2.SDef;
import com.ikags.animeeditor2.info.FrameInfo;
import com.ikags.animeeditor2.info.ImageModInfo;
import com.ikags.animeeditor2.info.ModInfo;
import com.ikags.animeeditor2.util.DrawUtil;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * <p>
 * Title:ika 动画编辑器 Description: 编辑图片和帧，形成动画，导出后给手机调用 Copyright: airzhangfish Copyright (c) 2007 blog: http://airzhangfish.spaces.live.com Company: Comicfishing author airzhangfish version 0.03a standard last updata 2007-8-23
 * </p>
 * 帧编辑界面，中间。
 */

public class FrameImage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Thread thread;

	public FrameImage() {
		addMouseListener(new MyListener());
		addMouseMotionListener(new MyListener());
	}

	int moveX;
	int moveY;
	static int frame_pressRect = -1;
	static boolean mod_canmove = false;
	int tp_x;
	int tp_y;

	class MyListener extends MouseInputAdapter {
		public void mouseMoved(MouseEvent e) {
			moveX = e.getX();
			moveY = e.getY();
			update_Srceen();
		}

		public void mousePressed(MouseEvent e) {
			// 左键，添加新物块
			int selectindex = FramePanel.wordList.getSelectedIndex();
			if (SwingUtilities.isLeftMouseButton(e) && FrameModImage.pressRect != -1) {
				if (selectindex < 0) {
					JOptionPane.showMessageDialog(null, "请选择或者创建一个新帧来放置物块", "出错", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int mod_frame_id = FrameModImage.pressRect;
				int mod_frame_x = (e.getX() - SDef.frame_total_x) / SDef.frame_size;
				int mod_frame_y = (e.getY() - SDef.frame_total_y) / SDef.frame_size;
				int mod_frame_tran = 0;

				FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(selectindex);

				ModInfo mod = new ModInfo();
				mod.mImageModID = mod_frame_id;
				mod.mX = mod_frame_x;
				mod.mY = mod_frame_y;
				mod.mRotate = mod_frame_tran;
				mod.mScale = 1.0f;

				fi.mModlist.add(mod);

				SDef.use_modlist.clear();
				for (int i = 0; i < fi.mModlist.size(); i++) {
					ModInfo moditem = fi.mModlist.elementAt(i);
					SDef.use_modlist.addElement(moditem.getName());
				}
				FramePanel.modlist.setSelectedIndex(0);
				FrameModImage.pressRect = -1;
				FramePanel.up_frame_list();
			}

			// 选取已有物块
			if (SwingUtilities.isLeftMouseButton(e) && FrameModImage.pressRect == -1 && selectindex >= 0) {
				FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(selectindex);

				for (int i = 0; i < fi.mModlist.size(); i++) {
					ModInfo moditem = fi.mModlist.elementAt(i);
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(moditem.mImageModID);
					int xx = moditem.mX * SDef.frame_size + SDef.frame_total_x;
					int ww = imi.mWidth * SDef.frame_size;
					int yy = moditem.mY * SDef.frame_size + SDef.frame_total_y;
					int hh = imi.mHeight * SDef.frame_size;

					if (e.getX() > xx && e.getX() < xx + ww && e.getY() > yy && e.getY() < yy + hh) {
						frame_pressRect = i;
						FramePanel.modlist.setSelectedIndex(frame_pressRect);
						mod_canmove = true;
						tp_x = e.getX() - moditem.mX;
						tp_y = e.getY() - moditem.mY;
						break;
					}
				}
			}

			if (SwingUtilities.isRightMouseButton(e)) {
				FrameModImage.pressRect = -1;
				frame_pressRect = -1;
				FramePanel.modlist.setSelectedIndex(frame_pressRect);

				tmp_move_x = e.getX() - SDef.frame_total_x;
				tmp_move_y = e.getY() - SDef.frame_total_y;
			}
			update_Srceen();
		}

		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e) && mod_canmove == true) {

				int selectindex = FramePanel.wordList.getSelectedIndex();
				if (selectindex >= 0) {
					FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(selectindex);
					ModInfo mod = fi.mModlist.get(frame_pressRect);
					mod.mX = e.getX() - tp_x;
					mod.mY = e.getY() - tp_y;
				}

			}

			if (SwingUtilities.isRightMouseButton(e)) {
				// SDef.frame_total_x;
				SDef.frame_total_x = e.getX() - tmp_move_x;
				SDef.frame_total_y = e.getY() - tmp_move_y;
			}
			moveX = e.getX();
			moveY = e.getY();
			update_Srceen();
		}

		public void mouseReleased(MouseEvent e) {
			mod_canmove = false;
			update_Srceen();
		}
	}

	public static int tmp_move_x;
	public static int tmp_move_y;
	// 更新资料
	int temp_seclet_list = 0;

	public void updata_use_mod_list() {
		int index = FramePanel.wordList.getSelectedIndex();
		if (temp_seclet_list != index) {
			frame_pressRect = -1;
			temp_seclet_list = index;
			if (index < 0) {
				return;
			}
			FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(index);
			SDef.use_modlist.clear();
			for (int i = 0; i < fi.mModlist.size(); i++) {
				ModInfo mi = fi.mModlist.elementAt(i);
				SDef.use_modlist.addElement(mi.getName());
			}
			FramePanel.modlist.setSelectedIndex(0);
		}
	}

	public void paint(Graphics g) {

		updata_use_mod_list();
		g.setColor(new Color(SDef.BG_R, SDef.BG_G, SDef.BG_B));
		g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(Math.abs(SDef.BG_R + 10), Math.abs(SDef.BG_G + 10), Math.abs(SDef.BG_B + 10)));
		g.fillRect(SDef.MO_X * SDef.frame_size + SDef.frame_total_x, SDef.MO_Y * SDef.frame_size + SDef.frame_total_y, SDef.MO_W * SDef.frame_size, SDef.MO_H * SDef.frame_size);
		g2.setColor(new Color(SDef.SG_R, SDef.SG_G, SDef.SG_B));
		int maxlength = Math.max(g.getClipBounds().width, g.getClipBounds().height);
		for (int i = 0; i < (maxlength / 16) + 1; i++) {
			g2.drawLine(0, 16 * i, maxlength, 16 * i);
			g2.drawLine(16 * i, 0, 16 * i, maxlength);
		}
		g2.setColor(new Color(SDef.LN_R, SDef.LN_G, SDef.LN_B));

		g.drawLine(SDef.frame_total_x, 0, SDef.frame_total_x, 1000);
		g.drawLine(0, SDef.frame_total_y, 1000, SDef.frame_total_y);
		g.drawLine(SDef.frame_total_x, 0, SDef.frame_total_x, 820);
		g.drawLine(0, SDef.frame_total_y, 1000, SDef.frame_total_y);

		// g.drawRect(SDef.MO_X, SDef.MO_Y, SDef.MO_W, SDef.MO_H);
		// 绘画
		g.setClip(0, 0, 1000, 1000);

		int selectindex = FramePanel.wordList.getSelectedIndex();
		FrameInfo fi = null;
		if (selectindex >= 0) {
			fi = SDef.mTotalInfo.mFramelist.elementAt(selectindex);
		}

		if (fi != null) {
			for (int i = 0; i < fi.mModlist.size(); i++) {
				ModInfo mod = fi.mModlist.elementAt(i);
				ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(mod.mImageModID);
				DrawUtil.drawAniPart20(imi.mImage, mod.mX * SDef.frame_size + SDef.frame_total_x, mod.mY * SDef.frame_size + SDef.frame_total_y, mod.mRotate, mod.mScale, g);
			}
		}

		// frame_pressRect 选取物块，白边范围
		if (fi != null && frame_pressRect != -1) {

			ModInfo mod = fi.mModlist.elementAt(frame_pressRect);
			g.setColor(new Color(250, 250, 250));
			g.setClip(0, 0, 1000, 1000);
			ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(mod.mImageModID);

			g.drawRect(mod.mX * SDef.frame_size + SDef.frame_total_x, mod.mY * SDef.frame_size + SDef.frame_total_y, imi.mWidth * SDef.frame_size, imi.mHeight * SDef.frame_size);

		}
		// 鼠标跟随的图片
		if (FrameModImage.pressRect != -1) {

			ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(FrameModImage.pressRect);

			g.drawImage(imi.mImage, moveX, moveY, null);
			g.setColor(new Color(250, 250, 250));
		}

		g2.setColor(new Color(SDef.LN_R, 100, 50));
		g2.drawLine(moveX, 0, moveX, 1000);
		g2.drawLine(0, moveY, 1000, moveY);

		g2.setColor(new Color(0, 0, 0));
		g2.drawString("scale="+SDef.frame_size+",pos=" + ((moveX) / SDef.mod_size) + "," + ((moveY) / SDef.mod_size), 50, 10);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void update_Srceen() {
		repaint();
	}

}
