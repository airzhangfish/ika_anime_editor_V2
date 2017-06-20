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
 * frame_panel ֡�������
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

	JButton Botton_MOVE_UP = new JButton("����");
	JButton Botton_MOVE_DOWN = new JButton("����");
	JButton Botton_MOVE_LEFT = new JButton("����");
	JButton Botton_MOVE_RIGHT = new JButton("����");
	private JScrollBar ratbar= new JScrollBar(Adjustable.HORIZONTAL, 0, 0, 0, 359);
	private JScrollBar scalebar= new JScrollBar(Adjustable.HORIZONTAL, 10, 0, 1, 100);
	JButton Botton_MOD_DEL = new JButton("ɾ�����");
	JButton Botton_BIG = new JButton("����Ŵ�");
	JButton Botton_SMALL = new JButton("�����С");
	JButton Botton_MOD_UP = new JButton("����һ��");
	JButton Botton_MOD_DOWN = new JButton("����һ��");
	JButton Botton_EXPLAIN = new JButton("���ע��");
	// JTextField displayTextField = new JTextField("�����汾Ԥ������λ��");
	JFormattedTextField displayTextField = new JFormattedTextField();

	JPanel left_Jpanel = new JPanel(new BorderLayout());
	JPanel middle_Jpanel = new JPanel(new BorderLayout());
	JPanel right_Jpanel = new JPanel(new BorderLayout());

	JPanel bottonPanel2 = new JPanel(new GridLayout(2, 3));
	JButton Botton_jia = new JButton("���֡");
	JButton Botton_jian = new JButton("ɾ��֡");
	JButton Botton_shang = new JButton("����֡");
	JButton Botton_xia = new JButton("����֡");
	JButton Botton_fuzhi = new JButton("����֡");

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
				// ����Ƿ�����ʹ��
				String usedanime = "";
				boolean can_del = true;
				for (int j = 0; j < temp.length; j++) {
					usedanime = usedanime + SDef.mTotalInfo.hasFramefromAnimelist(temp[j]);
				}
				if (usedanime.length() > 0) {
					can_del = false;
					usedanime = "���ڵ��ô�֡�Ķ����У�\n" + usedanime + "\n����ɾ��";
				}
				if (can_del == false) {
					JOptionPane.showMessageDialog(null, usedanime, "����", JOptionPane.ERROR_MESSAGE);
				} else {
					// ���û����ʹ�ã�ɾ��
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
					// �ƶ����ж������ݿ��е�����
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
				// ����
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

			// �����Ϣ
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
				// �����б�
				SDef.use_modlist.clear();
				up_frame_list();
				BigImagePanel.update_Srceen();
			}

			if (source == Botton_BIG) { // �Ŵ�
		
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

			if (source == Botton_SMALL) { // ��С
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
//			if (source == Botton_MOD_RAT) { // ��ת����
//				if (wordList.getSelectedIndex() >= 0 && FrameImage.frame_pressRect >= 0) {
//
//					// TODO ��תҪ��Ϊ���սǶ�
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
				//�޸�ѡ�еİ�ť����ת����
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
				//�޸�ѡ�еİ�ť����ת����
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
		// ��߿������
		left_Jpanel.add(list_frame_scrollPane, BorderLayout.NORTH);
		bottonPanel2.add(Botton_jia);
		bottonPanel2.add(Botton_jian);
		bottonPanel2.add(Botton_shang);
		bottonPanel2.add(Botton_xia);
		bottonPanel2.add(Botton_fuzhi);
		Botton_jia.setToolTipText("���һ֡��֡,����ʹ���߷�����������֡");
		Botton_jian.setToolTipText("ɾ���Ѵ���֡");
		Botton_shang.setToolTipText("��֡�����ƶ�");
		Botton_xia.setToolTipText("��֡�����ƶ�");
		Botton_fuzhi.setToolTipText("����ѡ��֡����֡�����������·�");
		Botton_jia.addActionListener(actionListener);
		Botton_jian.addActionListener(actionListener);
		Botton_shang.addActionListener(actionListener);
		Botton_xia.addActionListener(actionListener);
		Botton_fuzhi.addActionListener(actionListener);

		left_Jpanel.add(bottonPanel2, BorderLayout.CENTER);
		this.add(left_Jpanel, BorderLayout.WEST);

		// ��ť����
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
		ratbar.setToolTipText("�����ת");
		bottonPanel.add(scalebar);
		scalebar.addAdjustmentListener(adjustlstener);
		scalebar.setToolTipText("���������");
		
		
		Botton_BIG.setToolTipText("�û�֡����Ŵ�");
		Botton_MOVE_UP.setToolTipText("ѡ����������ƶ�");
		Botton_SMALL.setToolTipText("�û�֡������С");
		Botton_MOD_UP.setToolTipText("ѡ�����������ƶ�");
		Botton_EXPLAIN.setToolTipText("���֡���ע��");
		Botton_MOVE_LEFT.setToolTipText("ѡ����������ƶ�");
		Botton_MOVE_DOWN.setToolTipText("ѡ����������ƶ�");
		Botton_MOVE_RIGHT.setToolTipText("ѡ����������ƶ�");
		Botton_MOD_DEL.setToolTipText("ѡ����������ƶ�");
		Botton_MOD_DOWN.setToolTipText("ѡ����������ƶ�");

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
		// �м�������
		middle_Jpanel.add(BigImagePanel, BorderLayout.CENTER);
		middle_Jpanel.add(bottonPanel, BorderLayout.SOUTH);
		this.add(middle_Jpanel, BorderLayout.CENTER);

		// �ұ߿������Ч��
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
