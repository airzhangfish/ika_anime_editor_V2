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
 * 物块编辑画面，右上，预览用
 */

public class ModOneImage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Thread thread;

	public ModOneImage() {
		// this.setPreferredSize(new Dimension(820, 670));
		MyListener myListener = new MyListener();
		addMouseListener(myListener);
		addMouseMotionListener(myListener);
	}

	static int pressRect = -1;
	int change_xxx = 0;
	int change_yyy = 0;
	class MyListener extends MouseInputAdapter {

		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				change_xxx = e.getX() - xxx;
				change_yyy = e.getY() - yyy;
				update_Srceen();
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				xxx = e.getX() - change_xxx;
				yyy = e.getY() - change_yyy;
				update_Srceen();
			}
		}

	}

	int xxx = 10;
	int yyy = 10;
	public void paint(Graphics g) {
		g.setColor(new Color(SDef.BG_R, SDef.BG_G, SDef.BG_B));
		g.fillRect(0, 0, 820, 670);
		Graphics2D g2 = (Graphics2D) g;

		// 预览图
		int index = ModPanel.wordList.getSelectedIndex();
		if (index >= 0) {
			ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(index);
			if (imi.mImage != null) {
				g.drawImage(imi.mImage, xxx, yyy, null);
				g2.setColor(new Color(0, 0, 0));
			}else{
				g2.setColor(new Color(0, 0, 0));
				g2.drawString("no pic", xxx, yyy);
			}
		}

		g2.setColor(new Color(0, 0, 0));
		g2.drawString(xxx + "," + yyy + " X" + SDef.mod_size, 50, 10);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void update_Srceen() {
		repaint();
	}
}
