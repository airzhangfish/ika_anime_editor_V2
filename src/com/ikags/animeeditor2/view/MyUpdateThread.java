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
	 * Title:ika �����༭��
	 * </p>
	 * <p>
	 * Description: �༭ͼƬ��֡���γɶ�������������ֻ�����
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
	 * �߳̿��ơ���Ҫ���ڸ���һЩ��������
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
			// ��� mod panel��һЩ���ݴ���ѡ�
			if (ModPanel.wordList.getSelectedIndex() != mod_panel_wordList_getSelectedIndex) {
				mod_panel_wordList_getSelectedIndex = ModPanel.wordList.getSelectedIndex();
				ModPanel.smalImagePanel.update_Srceen();
			}
			// ��� frame_ panel��һЩ���ݴ���ѡ�
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
