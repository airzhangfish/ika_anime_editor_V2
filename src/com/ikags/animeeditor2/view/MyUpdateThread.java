package com.ikags.animeeditor2.view;

import com.ikags.animeeditor2.SDef;

public class MyUpdateThread implements Runnable {
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
	 * 线程控制。主要用于更新一些数据资料
	 */

	public MyUpdateThread() {
		thread = new Thread(this);
		thread.start();
	}

	int mod_panel_wordList_getSelectedIndex = -1;
	int frame_panel_modList_getSelectedIndex = -1;
	int frame_panel_wordList_getSelectedIndex = -1;
	public void check() {
		try {
			// 检测 mod panel的一些数据处理选项。
			if (ModPanel.wordList.getSelectedIndex() != mod_panel_wordList_getSelectedIndex) {
				mod_panel_wordList_getSelectedIndex = ModPanel.wordList.getSelectedIndex();
				ModPanel.smalImagePanel.update_Srceen();
			}
			// 检测 frame_ panel的一些数据处理选项。
			if (FramePanel.modlist.getSelectedIndex() != frame_panel_modList_getSelectedIndex) {
				frame_panel_modList_getSelectedIndex = FramePanel.modlist.getSelectedIndex();
				FrameImage.frame_pressRect = frame_panel_modList_getSelectedIndex;
				FramePanel.small_Imagepanel.update_Srceen();
				FramePanel.BigImagePanel.update_Srceen();
			}

			if (FramePanel.wordList.getSelectedIndex() != frame_panel_wordList_getSelectedIndex) {
				frame_panel_wordList_getSelectedIndex = FramePanel.wordList.getSelectedIndex();
				FramePanel.small_Imagepanel.update_Srceen();
				FramePanel.BigImagePanel.update_Srceen();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean isrun = true;
	public void run() {
		while (isrun == true) {
			check();
			try {
				Thread.sleep(SDef.SYSTEM_DELAY);
			} catch (InterruptedException ex) {}
		}
	}

}
