package com.ikags.animeeditor2;
import javax.swing.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import com.ikags.animeeditor2.info.TotalInfo;

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
 * 定义界面
 */

public class SDef {

	public static int SYSTEM_DELAY = 80;
	public static File binfile = null;
	public static int fangfa = 0;
	public static byte offset = 0;

	public static TotalInfo mTotalInfo = new TotalInfo();

	// public static BufferedImage[] mod_big_bfimage = new BufferedImage[1000];
	// public static BufferedImage[] static_mod_big_bfimage = new BufferedImage[1000];
	// public static String[] big_bfimage_path = new String[1000];
	public static int BG_R = 144;
	public static int BG_G = 144;
	public static int BG_B = 144;

	public static int SG_R = 160;
	public static int SG_G = 96;
	public static int SG_B = 160;

	public static int LN_R = 0;
	public static int LN_G = 0;
	public static int LN_B = 208;

	public static int MO_X = -120;
	public static int MO_Y = -160;
	public static int MO_W = 240;
	public static int MO_H = 320;
	public static int AMO_OFFX = 0;
	public static int AMO_OFFY= 0;
	
	
	public static int mod_size = 1; // 放大倍数
	public static int frame_size = 1; // 放大倍数

	// mod panel
	public static DefaultListModel mod_listModel = new DefaultListModel();
	public static DefaultListModel image_listModel = new DefaultListModel();

	// frame panel
	public static DefaultListModel frame_listModel = new DefaultListModel();

	public static DefaultListModel use_modlist = new DefaultListModel();

	// anime panel
	public static DefaultListModel use_framelistModel = new DefaultListModel();
	public static DefaultListModel anime_listModel = new DefaultListModel();

	public static void reset_new() {
		binfile = null;
		mod_size = 1; // 放大倍数
		frame_size = 1; // 放大倍数
		mod_listModel = new DefaultListModel();
		image_listModel = new DefaultListModel();
	}

	public static int frame_total_x = 300;
	public static int frame_total_y = 320;


}
