package com.ikags.animeeditor2.view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import com.ikags.animeeditor2.SDef;
import com.ikags.animeeditor2.info.BigImageInfo;
import com.ikags.animeeditor2.info.ImageModInfo;
import com.ikags.animeeditor2.util.IKAImageUtil;
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
 * 物块编辑面板中间，图像部分
 */

public class ModImage extends JPanel {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	public Thread thread;

	public ModImage() {
		this.setAutoscrolls(true);
		MyListener myListener = new MyListener();
		addMouseListener(myListener);
		addMouseMotionListener(myListener);
	}

	int Rect_X = 0;
	int Rect_Y = 0;
	int Rect_W = 0;
	int Rect_H = 0;

	int tp_X = 0;
	int tp_Y = 0;
	int tp_W = 0;
	int tp_H = 0;

	boolean insideRect = false;
	boolean drawRecting = false;
	static int pressRect = -1;
	boolean movemodRect = false;
	int temp_point_x;
	int temp_point_y;
	ModOneImage moimage;
	int Xline = 0;
	int Yline = 0;
	class MyListener extends MouseInputAdapter {

		public void mouseMoved(MouseEvent e) {
			Xline = e.getX() / SDef.mod_size * SDef.mod_size;
			Yline = e.getY() / SDef.mod_size * SDef.mod_size;
			update_Srceen();
		}

		public void mousePressed(MouseEvent e) {

			insideRect = false;
			if (SwingUtilities.isLeftMouseButton(e) && drawRecting == false) {
				// 如果在已有的mod的范围内
				for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
					// 按照比例放大
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(i);
					if (e.getX() > imi.mX * SDef.mod_size + xxx && e.getX() < imi.mX * SDef.mod_size + xxx + imi.mWidth * SDef.mod_size && e.getY() > imi.mY * SDef.mod_size + yyy && e.getY() < imi.mY * SDef.mod_size + yyy + imi.mHeight * SDef.mod_size
							&& imi.mBigImageID == ModPanel.ImageList.getSelectedIndex()) {
						pressRect = i;
						insideRect = true;
						movemodRect = true;
						temp_point_x = (e.getX() - xxx) / SDef.mod_size;
						temp_point_y = (e.getY() - yyy) / SDef.mod_size;
						tp_X = imi.mX;
						tp_Y = imi.mY;
						tp_W = imi.mWidth;
						tp_H = imi.mHeight;
						ModPanel.wordList.setSelectedIndex(pressRect);
						break;

					}
				}
				update_Srceen();
				ModPanel.smalImagePanel.update_Srceen();
			}
			// 如果不在已有的mod范围内，就创建新的范围
			if (SwingUtilities.isLeftMouseButton(e) && drawRecting == false && insideRect == false) {
				Rect_X = (e.getX() - xxx) / SDef.mod_size;
				Rect_Y = (e.getY() - yyy) / SDef.mod_size;
				Rect_W = 0;
				Rect_H = 0;
				drawRecting = true;
				update_Srceen();
			}
			// 拖拽功能，方便整体移动。
			else if (SwingUtilities.isRightMouseButton(e)) {
				change_xxx = e.getX() - xxx;
				change_yyy = e.getY() - yyy;
				update_Srceen();
			}
		}

		// 鼠标拖拽
		public void mouseDragged(MouseEvent e) {
			// 左键创建新区域，用鼠标绘画范围
			if (SwingUtilities.isLeftMouseButton(e) && drawRecting == true) {
				Rect_W = (e.getX() - Rect_X * SDef.mod_size - xxx) / SDef.mod_size;
				Rect_H = (e.getY() - Rect_Y * SDef.mod_size - yyy) / SDef.mod_size;
			}
			// 右键拖拽全局
			if (SwingUtilities.isRightMouseButton(e)) {
				xxx = (e.getX() - change_xxx) / SDef.mod_size * SDef.mod_size;
				yyy = (e.getY() - change_yyy) / SDef.mod_size * SDef.mod_size;
			}
			// 左键选择选取后拖动范围
			if (SwingUtilities.isLeftMouseButton(e) && movemodRect == true && insideRect == true) {

				ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(pressRect);

				imi.mX = (e.getX() - xxx) / SDef.mod_size - (temp_point_x - tp_X);
				imi.mY = (e.getY() - yyy) / SDef.mod_size - (temp_point_y - tp_Y);
				// list更新

				SDef.mod_listModel.clear();
				for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
					ImageModInfo spimi = SDef.mTotalInfo.mImageModlist.elementAt(i);
					SDef.mod_listModel.addElement(spimi.getName());
				}

				ModPanel.wordList.setSelectedIndex(pressRect);

				int selected = ModPanel.ImageList.getSelectedIndex();
				if (selected >= 0) {
					BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(selected);
					 imi.mImage = IKAImageUtil.create_mod_image(bii.mStaticBigImage, imi.mX, imi.mY, imi.mWidth, imi.mHeight);
					 imi.mStaticImage =imi.mImage;
				}
			}
			update_Srceen();
		}

		public void mouseReleased(MouseEvent e) {

			movemodRect = false; // 选定改变设置
			// 左键，绘画新切片
			if (SwingUtilities.isLeftMouseButton(e) && drawRecting == true && insideRect == false) {
				// 不允许长度宽度为0的动编出现
				if (Rect_W <= 0 || Rect_H <= 0) {
					drawRecting = false;
					return;
				}
				ImageModInfo imi = new ImageModInfo();
				imi.mX = Rect_X;
				imi.mY = Rect_Y;
				imi.mWidth = Rect_W;
				imi.mHeight = Rect_H;
				imi.mUsed = 1;
				imi.mBigImageID = ModPanel.ImageList.getSelectedIndex();
				if(imi.mBigImageID>=0){
					BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(imi.mBigImageID);
					imi.mImage = IKAImageUtil.create_mod_image(bii.mStaticBigImage, imi.mX, imi.mY, imi.mWidth, imi.mHeight);
					imi.mStaticImage = imi.mImage;
					SDef.mTotalInfo.addImageModInfo(imi);	
				}

				// list更新
				SDef.mod_listModel.clear();
				for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
					ImageModInfo spimi = SDef.mTotalInfo.mImageModlist.elementAt(i);
					SDef.mod_listModel.addElement(spimi.getName());
				}
				drawRecting = false;
				update_Srceen();
			}
		}

	}

	int xxx = 16;
	int yyy = 16;
	int change_xxx = 0;
	int change_yyy = 0;

	// 物块切换时候自动切换图片
	int oldnumber = -1;
	int oldnumber2 = -1;
	public void updata() {
		if (ModPanel.ImageList.getSelectedIndex() < 0) {
			return;
		}
		if (ModPanel.wordList.getSelectedIndex() < 0) {
			return;
		}
		int selectindex = ModPanel.ImageList.getSelectedIndex();

		if (oldnumber2 != selectindex) {
			SDef.mod_size = 1;
			BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(selectindex);
			bii.mBigImage = bii.mStaticBigImage;
			update_Srceen();
			oldnumber2 = ModPanel.ImageList.getSelectedIndex();
		}

		int modselect = ModPanel.wordList.getSelectedIndex();
		ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(modselect);
		if (oldnumber != imi.mBigImageID) {
			ModPanel.ImageList.setSelectedIndex(imi.mBigImageID);
			SDef.mod_size = 1;

			BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(imi.mBigImageID);
			bii.mBigImage = bii.mStaticBigImage;
			update_Srceen();
			oldnumber = imi.mBigImageID;
		}

	}

	public void paint(Graphics g) {
		g.setColor(new Color(SDef.BG_R, SDef.BG_G, SDef.BG_B));
		g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(SDef.SG_R, SDef.SG_G, SDef.SG_B));
		int maxlength = Math.max(g.getClipBounds().width, g.getClipBounds().height);
		for (int i = 0; i < (maxlength / 16) + 1; i++) {
			g2.drawLine(0, 16 * i, maxlength, 16 * i);
			g2.drawLine(16 * i, 0, 16 * i, maxlength);
		}

		updata();
		// 图像开始
		int imageselectIndex = ModPanel.ImageList.getSelectedIndex();
		if (imageselectIndex >= 0) {

			BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(imageselectIndex);

			if (bii.mBigImage != null) {
				g2.drawImage(bii.mBigImage, xxx, yyy, null);
			}
		}
		int[] temp = new int[ModPanel.wordList.getSelectedIndices().length];
		temp = ModPanel.wordList.getSelectedIndices();
		// 屏幕上的物块
		g2.setColor(new Color(88, 88, 88));
		for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
			ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(i);

			if (imi.mBigImageID == imageselectIndex) {
				g2.drawRect(imi.mX * SDef.mod_size + xxx, imi.mY * SDef.mod_size + yyy, imi.mWidth * SDef.mod_size, imi.mHeight * SDef.mod_size);
			}
		}
		// 选定的物块
		g2.setColor(new Color(188, 188, 188));
		for (int i = 0; i < temp.length; i++) {
			ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(temp[i]);
			if (imi.mBigImageID == imageselectIndex) {
				g2.drawRect(imi.mX * SDef.mod_size + xxx, imi.mY * SDef.mod_size + yyy, imi.mWidth * SDef.mod_size, imi.mHeight * SDef.mod_size);

			}
		}
		// 正在绘画
		if (drawRecting == true) {
			g2.setColor(new Color(44, 44, 44));
			g2.drawRect(Rect_X * SDef.mod_size + xxx, Rect_Y * SDef.mod_size + yyy, Rect_W * SDef.mod_size, Rect_H * SDef.mod_size);
		}
		g2.setColor(new Color(SDef.LN_R, SDef.LN_G, SDef.LN_B));
		g2.drawLine(Xline, 0, Xline, 1000);
		g2.drawLine(0, Yline, 1000, Yline);

		// 图像结束

		// g2.setColor(color2);
		// g.drawLine(410, 0, 410, 820);
		g2.setColor(new Color(0, 0, 0));
		g2.drawString(xxx + "," + yyy + ",SIZE=" + SDef.mod_size + ",pos=" + ((Xline - xxx) / SDef.mod_size) + "," + ((Yline - yyy) / SDef.mod_size), 50, 10);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void update_Srceen() {
		repaint();
		xxx = xxx / SDef.mod_size * SDef.mod_size;
		yyy = yyy / SDef.mod_size * SDef.mod_size;
	}

}
