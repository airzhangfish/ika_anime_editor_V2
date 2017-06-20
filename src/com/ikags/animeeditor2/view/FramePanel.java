package com.ikags.animeeditor2.view;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ikags.animeeditor2.SDef;
import com.ikags.animeeditor2.info.AnimeInfo;
import com.ikags.animeeditor2.info.FrameInfo;
import com.ikags.animeeditor2.info.ImageModInfo;
import com.ikags.animeeditor2.info.ModInfo;
import com.ikags.animeeditor2.util.DrawUtil;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

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
 * frame_panel 帧控制面板
 */

public class FramePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Thread thread;

	public static JList wordList = new JList(SDef.frame_listModel);
	public static JList modlist = new JList(SDef.use_modlist);
	JScrollPane list_frame_scrollPane = new JScrollPane(wordList);
	public static FrameImage BigImagePanel = new FrameImage();
	JPanel bottonPanel = new JPanel(new GridLayout(2, 6));
	public static FrameModImage small_Imagepanel = new FrameModImage();

	JButton Botton_MOVE_UP = new JButton("上移");
	JButton Botton_MOVE_DOWN = new JButton("下移");
	JButton Botton_MOVE_LEFT = new JButton("左移");
	JButton Botton_MOVE_RIGHT = new JButton("右移");
	private JScrollBar ratbar= new JScrollBar(Adjustable.HORIZONTAL, 0, 0, 0, 359);
	private JScrollBar scalebar= new JScrollBar(Adjustable.HORIZONTAL, 10, 0, 1, 100);
	JButton Botton_MOD_DEL = new JButton("删除物块");
	JButton Botton_BIG = new JButton("浏览放大");
	JButton Botton_SMALL = new JButton("浏览缩小");
	JButton Botton_MOD_UP = new JButton("上移一层");
	JButton Botton_MOD_DOWN = new JButton("下移一层");
	JButton Botton_EXPLAIN = new JButton("添加注释");
	// JTextField displayTextField = new JTextField("升级版本预留功能位置");
	JFormattedTextField displayTextField = new JFormattedTextField();

	JPanel left_Jpanel = new JPanel(new BorderLayout());
	JPanel middle_Jpanel = new JPanel(new BorderLayout());
	JPanel right_Jpanel = new JPanel(new BorderLayout());

	JPanel bottonPanel2 = new JPanel(new GridLayout(2, 3));
	JButton Botton_jia = new JButton("添加帧");
	JButton Botton_jian = new JButton("删除帧");
	JButton Botton_shang = new JButton("上移帧");
	JButton Botton_xia = new JButton("下移帧");
	JButton Botton_fuzhi = new JButton("复制帧");

	JScrollPane list_usemod_scrollPane = new JScrollPane(modlist);

	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			Object source = actionEvent.getSource();
			if (source == Botton_jia) {

				FrameInfo fi = new FrameInfo();
				SDef.mTotalInfo.addFrameInfo(fi);

				SDef.frame_listModel.clear();
				for (int i = 0; i < SDef.mTotalInfo.mFramelist.size(); i++) {
					FrameInfo mfi = SDef.mTotalInfo.mFramelist.elementAt(i);
					SDef.frame_listModel.addElement(mfi.getName());
				}
				wordList.setSelectedIndex(SDef.mTotalInfo.mFramelist.size() - 1);
			}

			if (source == Botton_jian) {

				int[] temp = new int[wordList.getSelectedIndices().length];
				temp = wordList.getSelectedIndices();
				// 检查是否有人使用
				String usedanime = "";
				boolean can_del = true;
				for (int j = 0; j < temp.length; j++) {
					usedanime = usedanime + SDef.mTotalInfo.hasFramefromAnimelist(temp[j]);
				}
				if (usedanime.length() > 0) {
					can_del = false;
					usedanime = "正在调用此帧的动画有：\n" + usedanime + "\n不能删除";
				}
				if (can_del == false) {
					JOptionPane.showMessageDialog(null, usedanime, "出错", JOptionPane.ERROR_MESSAGE);
				} else {
					// 如果没有人使用，删除
					int tempseclet = wordList.getSelectedIndex();
					for (int j = 0; j < temp.length; j++) {
						SDef.mTotalInfo.removeFrameInfo(temp[j]);
					}
					up_frame_list();
					if (tempseclet < 1) {
						wordList.setSelectedIndex(0);
					} else {
						wordList.setSelectedIndex(tempseclet - 1);
					}
					// 移动所有动画数据库中的数据
					for (int j = 0; j < temp.length; j++) {
						SDef.mTotalInfo.removeFrameFromAnimeList(temp[j]);
					}
				}
			}

			if (source == Botton_shang) {
				int getSI = wordList.getSelectedIndex();
				boolean isUp = SDef.mTotalInfo.moveFrameUp(getSI);
				up_frame_list();
				wordList.setSelectedIndex(getSI - 1);
				SDef.mTotalInfo.moveFrameUpFromAnime(getSI);
			}

			if (source == Botton_xia) {

				int getSI = wordList.getSelectedIndex();
				if (getSI > SDef.mTotalInfo.mFramelist.size() - 2) {
					return;
				}
				boolean isUp = SDef.mTotalInfo.moveFrameUp(getSI);
				up_frame_list();
				wordList.setSelectedIndex(getSI + 1);
				SDef.mTotalInfo.moveFrameDownFromAnime(getSI);
			}

			if (source == Botton_fuzhi) {
				int getSI = wordList.getSelectedIndex();
				if (getSI < 0) {
					return;
				}
				System.out.println("Botton_fuzhi="+getSI);
				// 复制
				FrameInfo oldfi = SDef.mTotalInfo.mFramelist.get(getSI);
				FrameInfo fi = new FrameInfo();
				fi.mInfo = new String(oldfi.mInfo);
				for (int i = 0; i < oldfi.mModlist.size(); i++) {
					ModInfo oldmi = oldfi.mModlist.elementAt(i);
					ModInfo mi = new ModInfo();
					mi.mX = oldmi.mX;
					mi.mY = oldmi.mY;
					mi.mRotateType = oldmi.mRotateType;
					mi.mRotate = oldmi.mRotate;
					mi.mScale = oldmi.mScale;
					mi.mInfo = new String(oldmi.mInfo);
					mi.mImageModID = oldmi.mImageModID;
					fi.mModlist.add(mi);
				}
				System.out.println("frameinfo="+fi.getName());
				SDef.mTotalInfo.addFrameInfo(fi);
				up_frame_list();
				wordList.setSelectedIndex(SDef.mTotalInfo.mFramelist.size() - 1);
				System.out.println("add fuzhi_success");
			}

			// 面板信息
			if (source == Botton_MOVE_UP) {
				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect != -1) {
					FrameInfo fi = SDef.mTotalInfo.mFramelist.get(wordList.getSelectedIndex());
					ModInfo mod = fi.mModlist.elementAt(FrameImage.frame_pressRect);
					mod.mY--;
				}
				BigImagePanel.update_Srceen();
			}
			if (source == Botton_MOVE_DOWN) {
				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect != -1) {
					FrameInfo fi = SDef.mTotalInfo.mFramelist.get(wordList.getSelectedIndex());
					ModInfo mod = fi.mModlist.elementAt(FrameImage.frame_pressRect);
					mod.mY++;
				}
				BigImagePanel.update_Srceen();
			}
			if (source == Botton_MOVE_LEFT) {
				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect != -1) {
					FrameInfo fi = SDef.mTotalInfo.mFramelist.get(wordList.getSelectedIndex());
					ModInfo mod = fi.mModlist.elementAt(FrameImage.frame_pressRect);
					mod.mX--;
				}
				BigImagePanel.update_Srceen();
			}
			if (source == Botton_MOVE_RIGHT) {
				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect != -1) {
					FrameInfo fi = SDef.mTotalInfo.mFramelist.get(wordList.getSelectedIndex());
					ModInfo mod = fi.mModlist.elementAt(FrameImage.frame_pressRect);
					mod.mX++;
				}
				BigImagePanel.update_Srceen();
			}

			if (source == Botton_MOD_DEL) {
				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect != -1) {

					FrameInfo fi = SDef.mTotalInfo.mFramelist.get(wordList.getSelectedIndex());
					fi.removeModInfo(FrameImage.frame_pressRect);
					FrameImage.frame_pressRect = -1;
				}
				// 更新列表
				SDef.use_modlist.clear();
				up_frame_list();
				BigImagePanel.update_Srceen();
			}

			if (source == Botton_BIG) { // 放大
		
				SDef.frame_size++;
				if(SDef.frame_size>=8){
					SDef.frame_size=8;
					Botton_BIG.setEnabled(false);
					Botton_SMALL.setEnabled(true);
				}
				for (int i = 0; i < SDef.mod_listModel.getSize(); i++) {
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(i);
					//imi.mStaticImage = imi.mImage;
					imi.mImage = DrawUtil.getbigimage(imi.mStaticImage);
				}

//				Botton_BIG.setEnabled(false);
//				Botton_SMALL.setEnabled(true);
				BigImagePanel.update_Srceen();
			}

			if (source == Botton_SMALL) { // 缩小
				SDef.frame_size--;
				if(SDef.frame_size<=1){
					SDef.frame_size=1;	
					Botton_BIG.setEnabled(true);
					Botton_SMALL.setEnabled(false);
				}
				for (int i = 0; i < SDef.mod_listModel.getSize(); i++) {
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(i);
					if(SDef.frame_size==1){
						imi.mImage =imi.mStaticImage;
					}else{
					imi.mImage = DrawUtil.getbigimage(imi.mStaticImage);
					}
				}
//				Botton_BIG.setEnabled(true);
//				Botton_SMALL.setEnabled(false);
				BigImagePanel.update_Srceen();
			}

			if (source == Botton_EXPLAIN) {

				if (wordList.getSelectedIndex() >= 0) {
					FrameInfo finfo = SDef.mTotalInfo.mFramelist.elementAt(wordList.getSelectedIndex());
					finfo.mInfo = displayTextField.getText();
				}
				up_frame_list();
			}

			//TODO
//			if (source == Botton_MOD_RAT) { // 旋转处理
//				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect >= 0) {
//
//					// TODO 旋转要改为按照角度
//
//					// SDef.G_framebox[wordList.getSelectedIndex()][2 + 4 * frame_image.frame_pressRect + 3]++;
//					// SDef.G_framebox[wordList.getSelectedIndex()][2 + 4 * frame_image.frame_pressRect + 3] = SDef.G_framebox[wordList.getSelectedIndex()][2 +
//					// 4 * frame_image.frame_pressRect + 3] % 8;
//
//				}
//				BigImagePanel.update_Srceen();
//			}

			if (source == Botton_MOD_UP) {
				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect > 0) {
                  
					FrameInfo fi = SDef.mTotalInfo.mFramelist.get(wordList.getSelectedIndex());
					boolean ischange=fi.moveModUp(FrameImage.frame_pressRect);

					if(ischange){
						SDef.use_modlist.clear();
						for (int i = 0; i < fi.mModlist.size(); i++) {
							ModInfo modinfo = fi.mModlist.elementAt(i);
							SDef.use_modlist.addElement(modinfo.getName());
						}
						FrameImage.frame_pressRect--;
						modlist.setSelectedIndex(FrameImage.frame_pressRect);
						up_frame_list();	
					}
				}
				BigImagePanel.update_Srceen();
			}

			if (source == Botton_MOD_DOWN) {

				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect >= 0) {

					FrameInfo fi = SDef.mTotalInfo.mFramelist.get(wordList.getSelectedIndex());
					boolean ischange=fi.moveModDown(FrameImage.frame_pressRect);
					if(ischange){
					SDef.use_modlist.clear();
					for (int i = 0; i < fi.mModlist.size(); i++) {
						ModInfo modinfo = fi.mModlist.elementAt(i);
						SDef.use_modlist.addElement(modinfo.getName());
					}
					FrameImage.frame_pressRect++;
					modlist.setSelectedIndex(FrameImage.frame_pressRect);
					up_frame_list();
					}
				}
				BigImagePanel.update_Srceen();

			}
		}
	};
	
	
	AdjustmentListener adjustlstener=new AdjustmentListener(){

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			Object source=e.getSource();
			if(source==ratbar){
				//修改选中的按钮的旋转参数
				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect != -1) {
					FrameInfo fi = SDef.mTotalInfo.mFramelist.get(wordList.getSelectedIndex());
					ModInfo mod = fi.mModlist.elementAt(FrameImage.frame_pressRect);
					mod.mRotate=ratbar.getValue();
					System.out.println("mod.mRotate="+mod.mRotate);
					SDef.use_modlist.clear();
					for (int i = 0; i < fi.mModlist.size(); i++) {
						ModInfo modinfo = fi.mModlist.elementAt(i);
						SDef.use_modlist.addElement(modinfo.getName());
					}
					modlist.setSelectedIndex(FrameImage.frame_pressRect);
				}
				BigImagePanel.update_Srceen();
				
			}
			
			
			if(source==scalebar){
				//修改选中的按钮的旋转参数
				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect != -1) {
					FrameInfo fi = SDef.mTotalInfo.mFramelist.get(wordList.getSelectedIndex());
					ModInfo mod = fi.mModlist.elementAt(FrameImage.frame_pressRect);
					mod.mScale=(float)(scalebar.getValue())/10;
					System.out.println("mod.mScale="+mod.mScale);
					SDef.use_modlist.clear();
					for (int i = 0; i < fi.mModlist.size(); i++) {
						ModInfo modinfo = fi.mModlist.elementAt(i);
						SDef.use_modlist.addElement(modinfo.getName());
					}
					modlist.setSelectedIndex(FrameImage.frame_pressRect);
				}
				BigImagePanel.update_Srceen();
				
			}
			
		}
		
		
	};
	
	
	public static void up_frame_list() {

		int tempindex = FramePanel.wordList.getSelectedIndex();
		SDef.frame_listModel.clear();
		for (int i = 0; i < SDef.mTotalInfo.mFramelist.size(); i++) {
			FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(i);
			SDef.frame_listModel.addElement(fi.getName());
		}
		FramePanel.wordList.setSelectedIndex(tempindex);
	}
	
	ListSelectionListener lsl=new ListSelectionListener(){
//TODO
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int index=e.getFirstIndex();
			try{
				System.out.println("valueChanged_index="+index+","+e.getValueIsAdjusting());
			if(index>-1){
//				SDef.use_modlist.elementAt(index);
				int fameindex = FramePanel.wordList.getSelectedIndex();
				FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(fameindex);
				ModInfo mi=fi.mModlist.elementAt(index);
				ratbar.removeAdjustmentListener(adjustlstener);
				scalebar.removeAdjustmentListener(adjustlstener);
				ratbar.setValue(mi.mRotate);
				scalebar.setValue((int)(mi.mScale*10));
				
				ratbar.addAdjustmentListener(adjustlstener);
				scalebar.addAdjustmentListener(adjustlstener);
				
			}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		
	};


	public FramePanel() {
		setLayout(new BorderLayout());
		list_frame_scrollPane.setPreferredSize(new Dimension(200, 600));
		// 左边控制面板
		left_Jpanel.add(list_frame_scrollPane, BorderLayout.NORTH);
		bottonPanel2.add(Botton_jia);
		bottonPanel2.add(Botton_jian);
		bottonPanel2.add(Botton_shang);
		bottonPanel2.add(Botton_xia);
		bottonPanel2.add(Botton_fuzhi);
		Botton_jia.setToolTipText("添加一帧新帧,允许使用者放置物块在这个帧");
		Botton_jian.setToolTipText("删除已存在帧");
		Botton_shang.setToolTipText("此帧向上移动");
		Botton_xia.setToolTipText("此帧向下移动");
		Botton_fuzhi.setToolTipText("复制选中帧，新帧被放置在最下方");
		Botton_jia.addActionListener(actionListener);
		Botton_jian.addActionListener(actionListener);
		Botton_shang.addActionListener(actionListener);
		Botton_xia.addActionListener(actionListener);
		Botton_fuzhi.addActionListener(actionListener);

		left_Jpanel.add(bottonPanel2, BorderLayout.CENTER);
		this.add(left_Jpanel, BorderLayout.WEST);

		// 按钮控制
		bottonPanel.setPreferredSize(new Dimension(50, 50));
		bottonPanel.add(Botton_BIG);
		bottonPanel.add(Botton_MOVE_UP);
		bottonPanel.add(Botton_SMALL);
		bottonPanel.add(Botton_MOD_UP);
		bottonPanel.add(Botton_MOD_DEL);
		bottonPanel.add(Botton_EXPLAIN);

		bottonPanel.add(Botton_MOVE_LEFT);
		bottonPanel.add(Botton_MOVE_DOWN);
		bottonPanel.add(Botton_MOVE_RIGHT);
		bottonPanel.add(Botton_MOD_DOWN);
		bottonPanel.add(ratbar);
		ratbar.addAdjustmentListener(adjustlstener);
		ratbar.setToolTipText("物块旋转");
		bottonPanel.add(scalebar);
		scalebar.addAdjustmentListener(adjustlstener);
		scalebar.setToolTipText("用物块缩放");
		
		
		Botton_BIG.setToolTipText("用户帧界面放大");
		Botton_MOVE_UP.setToolTipText("选中物块向上移动");
		Botton_SMALL.setToolTipText("用户帧界面缩小");
		Botton_MOD_UP.setToolTipText("选中物块向里层移动");
		Botton_EXPLAIN.setToolTipText("向此帧添加注释");
		Botton_MOVE_LEFT.setToolTipText("选中物块向左移动");
		Botton_MOVE_DOWN.setToolTipText("选中物块向下移动");
		Botton_MOVE_RIGHT.setToolTipText("选中物块向右移动");
		Botton_MOD_DEL.setToolTipText("选中物块向上移动");
		Botton_MOD_DOWN.setToolTipText("选中物块向表层移动");

		Botton_BIG.setEnabled(true);
		Botton_SMALL.setEnabled(true);
		Botton_MOVE_UP.registerKeyboardAction(actionListener, "Botton_MOVE_UP", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		Botton_MOVE_DOWN.registerKeyboardAction(actionListener, "Botton_MOVE_DOWN", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		Botton_MOVE_LEFT.registerKeyboardAction(actionListener, "Botton_MOVE_LEFT", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		Botton_MOVE_RIGHT.registerKeyboardAction(actionListener, "Botton_MOVE_RIGHT", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		Botton_MOD_DEL.registerKeyboardAction(actionListener, "Botton_MOD_DEL", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		Botton_MOVE_UP.addActionListener(actionListener);
		Botton_MOVE_DOWN.addActionListener(actionListener);
		Botton_MOVE_LEFT.addActionListener(actionListener);
		Botton_MOVE_RIGHT.addActionListener(actionListener);
		Botton_MOD_DEL.addActionListener(actionListener);
		Botton_BIG.addActionListener(actionListener);
		Botton_SMALL.addActionListener(actionListener);
		Botton_MOD_UP.addActionListener(actionListener);
		Botton_MOD_DOWN.addActionListener(actionListener);
		Botton_EXPLAIN.addActionListener(actionListener);
		// 中间控制面板
		middle_Jpanel.add(BigImagePanel, BorderLayout.CENTER);
		middle_Jpanel.add(bottonPanel, BorderLayout.SOUTH);
		this.add(middle_Jpanel, BorderLayout.CENTER);

		// 右边控制面板效率
		right_Jpanel.setPreferredSize(new Dimension(200, 100));
		small_Imagepanel.setPreferredSize(new Dimension(200, 200));
		// displayTextField.setPreferredSize(new Dimension(200, 200));
		right_Jpanel.add(small_Imagepanel, BorderLayout.NORTH);
		right_Jpanel.add(list_usemod_scrollPane, BorderLayout.CENTER);
		right_Jpanel.add(displayTextField, BorderLayout.SOUTH);
		this.add(right_Jpanel, BorderLayout.EAST);
		this.registerKeyboardAction(actionListener, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
//		JScrollPane list_usemod_scrollPane = new JScrollPane(modlist);
		modlist.addListSelectionListener(lsl);
		
		
	}
}
