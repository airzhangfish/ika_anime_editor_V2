package com.ikags.animeeditor2.view;
import javax.swing.*;

import com.ikags.animeeditor2.SDef;
import com.ikags.animeeditor2.info.AnimeInfo;
import com.ikags.animeeditor2.info.FrameInfo;
import com.ikags.animeeditor2.info.ImageModInfo;
import com.ikags.animeeditor2.util.DrawUtil;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
 * anime_panel �����༭����İ�ť�Ͳ˵�����
 */

public class AnimePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Thread thread;

	JFormattedTextField displayTextField = new JFormattedTextField();
	public static JList framelist = new JList(SDef.frame_listModel);
	public static JList useframelist = new JList(SDef.use_framelistModel);
	public static JList animelist = new JList(SDef.anime_listModel);

	JScrollPane framelist_scrollPane = new JScrollPane(framelist);
	JScrollPane useframelist_scrollPane = new JScrollPane(useframelist);
	JScrollPane animelist_scrollPane = new JScrollPane(animelist);

	public static AnimeImage BigImagePanel = new AnimeImage();
	JPanel bottonPanel = new JPanel(new GridLayout(2, 6));
	public static AnimeFrameImage small_Imagepanel = new AnimeFrameImage();

	JPanel left_Jpanel = new JPanel(new BorderLayout());
	JPanel middle_Jpanel = new JPanel(new BorderLayout());
	JPanel right_Jpanel = new JPanel(new BorderLayout());

	JPanel bottonPanel2 = new JPanel(new GridLayout(2, 5));
	JPanel bottonPanel3 = new JPanel(new GridLayout(2, 3));

	JButton Botton_anime_jia = new JButton("��Ӷ���");
	JButton Botton_anime_jian = new JButton("ɾ������");
	JButton Botton_anime_shang = new JButton("���ƶ���");
	JButton Botton_anime_xia = new JButton("���ƶ���");
	JButton Botton_anime_fuzhi = new JButton("���ƶ���");
	JButton Botton_anime_play = new JButton("���Ŷ���");
	JButton Botton_anime_stop = new JButton("��ͣ����");

	JButton Botton_anime_bigger = new JButton("�Ŵ�");
	JButton Botton_anime_smaller = new JButton("��С");
	
	JButton Botton_frame_shang = new JButton("����ʹ��֡");
	JButton Botton_frame_xia = new JButton("����ʹ��֡");
	JButton Botton_frame_del = new JButton("ɾ��ʹ��֡");
	JButton Botton_frame_jia = new JButton("��֡������");

	JButton Botton_anime_explain = new JButton("���ע��");

	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			Object source = actionEvent.getSource();

			
			if (source == Botton_anime_bigger) {
				SDef.frame_size++;
				if(SDef.frame_size>=8){
					SDef.frame_size=8;
				}
				for (int i = 0; i < SDef.mod_listModel.getSize(); i++) {
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(i);
					imi.mImage = DrawUtil.getbigimage(imi.mStaticImage);
				}
				BigImagePanel.update_Srceen();
				small_Imagepanel.update_Srceen();
			}
			if (source == Botton_anime_smaller) {
				SDef.frame_size--;
				if(SDef.frame_size<=1){
					SDef.frame_size=1;	
				}
				for (int i = 0; i < SDef.mod_listModel.getSize(); i++) {
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.get(i);
					if(SDef.frame_size==1){
						imi.mImage =imi.mStaticImage;
					}else{
					imi.mImage = DrawUtil.getbigimage(imi.mStaticImage);
					}
				}
				BigImagePanel.update_Srceen();
				small_Imagepanel.update_Srceen();
			}
			
			
			// ����
			if (source == Botton_anime_jia) {

				AnimeInfo ainfo = new AnimeInfo();
				SDef.mTotalInfo.addAnimeInfo(ainfo);

				update_animelist();
				animelist.setSelectedIndex(SDef.mTotalInfo.mAnimelist.size() - 1);
				useframelist.setSelectedIndex(-1);

			}

			if (source == Botton_anime_jian) {
				int[] temp = new int[animelist.getSelectedIndices().length];
				temp = animelist.getSelectedIndices();
				int tempseclet = animelist.getSelectedIndex();

				for (int j = 0; j < temp.length; j++) {
					SDef.mTotalInfo.removeAnimeInfo(temp[j]);
				}
				update_animelist();
				if (tempseclet < 1) {
					animelist.setSelectedIndex(0);
				} else {
					animelist.setSelectedIndex(tempseclet - 1);
				}
			}

			if (source == Botton_anime_shang) {
				int getSI = animelist.getSelectedIndex();
				if (getSI <= 0) {
					return;
				}

				boolean isUp = SDef.mTotalInfo.moveAnimeUp(getSI);
				update_animelist();
				animelist.setSelectedIndex(getSI - 1);
			}

			if (source == Botton_anime_xia) {

				int getSI = animelist.getSelectedIndex();
				if (getSI > SDef.mTotalInfo.mAnimelist.size() - 2) {
					return;
				}

				boolean isDown = SDef.mTotalInfo.moveAnimeDown(getSI);
				update_animelist();
				animelist.setSelectedIndex(getSI + 1);
			}

			if (source == Botton_anime_fuzhi) {
				int getSI = animelist.getSelectedIndex();

				// ����
				AnimeInfo oldani = SDef.mTotalInfo.mAnimelist.get(getSI);
				AnimeInfo ani = new AnimeInfo();

				ani.mInfo = new String(oldani.mInfo);
				for (int i = 0; i < oldani.mFrameIDlist.size(); i++) {
					int oldint = oldani.mFrameIDlist.elementAt(i).intValue();
					ani.addFrameInfo(oldint);
				}
				SDef.mTotalInfo.addAnimeInfo(ani);

				update_animelist();
				animelist.setSelectedIndex(SDef.mTotalInfo.mAnimelist.size() - 1);
			}
			if (source == Botton_anime_play) {
				AnimeImage.anime_play = !AnimeImage.anime_play;
			}
			if (source == Botton_anime_stop) {
				AnimeImage.anime_play = !AnimeImage.anime_play;
			}

			// ֡����
			if (source == Botton_frame_shang) { // ��
				int useframeSI = useframelist.getSelectedIndex();
				if (useframeSI < 1) {
					return;
				}
				int getAnimeSI = animelist.getSelectedIndex();
				if (getAnimeSI < 0) {
					return;
				}

				AnimeInfo anime = SDef.mTotalInfo.mAnimelist.elementAt(getAnimeSI);
				anime.moveFrameUp(useframeSI);

				// ��ʵ����ˢ��
				update_usedFramelist(anime);
				useframelist.setSelectedIndex(useframeSI - 1);
			}

			if (source == Botton_frame_xia) { // ��
				int getAnimeSI = animelist.getSelectedIndex();
				int useframeSI = useframelist.getSelectedIndex();
				if (getAnimeSI < 0) {
					return;
				}
				AnimeInfo anime = SDef.mTotalInfo.mAnimelist.elementAt(getAnimeSI);
				if (useframeSI < 0 || useframeSI >= anime.mFrameIDlist.size() - 1) {
					return;
				}
				anime.moveFrameDown(useframeSI);

				// ��ʵ����ˢ��
				update_usedFramelist(anime);
				useframelist.setSelectedIndex(useframeSI + 1);
			}

			// ɾ��
			if (source == Botton_frame_del) {
				int useframeSI = useframelist.getSelectedIndex();
				if (useframeSI < 0) {
					return;
				}
				int getAnimeSI = animelist.getSelectedIndex();
				if (getAnimeSI < 0) {
					return;
				}

				AnimeInfo anime = SDef.mTotalInfo.mAnimelist.elementAt(getAnimeSI);
				anime.removeFrameInfo(useframeSI);

				// ��ʵ����ˢ��
				update_usedFramelist(anime);

				if (useframeSI < 1) {
					useframelist.setSelectedIndex(0);
				} else {
					useframelist.setSelectedIndex(useframeSI - 1);
				}
			}

			if (source == Botton_anime_explain) {
				int tempseclet = animelist.getSelectedIndex();
				if (tempseclet > -1) {
					AnimeInfo anime = SDef.mTotalInfo.mAnimelist.elementAt(tempseclet);
					anime.mInfo = displayTextField.getText();
				}
				update_animelist();
				animelist.setSelectedIndex(tempseclet);
			}

			if (source == Botton_frame_jia) {
				// ��֡��ID���뵽G_animebox��
				int getframeSI = framelist.getSelectedIndex();
				if (getframeSI < 0) {
					return;
				}
				int getAnimeSI = animelist.getSelectedIndex();
				if (getAnimeSI < 0) {
					return;
				}

				int frameID = getframeSI;
				AnimeInfo ainfo = SDef.mTotalInfo.mAnimelist.elementAt(getAnimeSI);
				ainfo.addFrameInfo(frameID);

				update_usedFramelist(ainfo);
				useframelist.setSelectedIndex(ainfo.mFrameIDlist.size() - 1);
			}

		}
	};

	public void update_animelist() {

		SDef.anime_listModel.clear();
		for (int i = 0; i < SDef.mTotalInfo.mAnimelist.size(); i++) {
			AnimeInfo ainfo = SDef.mTotalInfo.mAnimelist.elementAt(i);
			SDef.anime_listModel.addElement(ainfo.getName());
		}

	}

	public void update_usedFramelist(AnimeInfo anime) {
		SDef.use_framelistModel.clear();
		for (int i = 0; i < anime.mFrameIDlist.size(); i++) {
			int frameid = anime.mFrameIDlist.elementAt(i).intValue();
			SDef.use_framelistModel.addElement("frame=" + frameid);
		}
	}

	public AnimePanel() {
		setLayout(new BorderLayout());
		framelist_scrollPane.setPreferredSize(new Dimension(200, 700));
		// ��߿������
		left_Jpanel.setPreferredSize(new Dimension(200, 700));
		bottonPanel3.setPreferredSize(new Dimension(200, 50));
		bottonPanel3.add(Botton_frame_shang);
		bottonPanel3.add(Botton_frame_xia);
		bottonPanel3.add(Botton_frame_del);
		bottonPanel3.add(Botton_frame_jia);
		Botton_frame_shang.setToolTipText("�����ƶ�ѡ�е�ʹ��֡");
		Botton_frame_xia.setToolTipText("�����ƶ�ѡ�е�ʹ��֡");
		Botton_frame_del.setToolTipText("ɾ��ѡ�е�ʹ��֡");
		Botton_frame_jia.setToolTipText("���ѡ�е�֡��ʹ��֡�б�");
		Botton_frame_shang.addActionListener(actionListener);
		Botton_frame_xia.addActionListener(actionListener);
		Botton_frame_del.addActionListener(actionListener);
		Botton_frame_jia.addActionListener(actionListener);
		left_Jpanel.add(bottonPanel3, BorderLayout.NORTH);
		left_Jpanel.add(framelist_scrollPane, BorderLayout.CENTER);
		left_Jpanel.add(displayTextField, BorderLayout.SOUTH);
		this.add(left_Jpanel, BorderLayout.WEST);

		// �м�������
		useframelist_scrollPane.setPreferredSize(new Dimension(200, 350));
		animelist_scrollPane.setPreferredSize(new Dimension(200, 350));
		bottonPanel2.add(Botton_anime_jia);
		bottonPanel2.add(Botton_anime_jian);
		bottonPanel2.add(Botton_anime_shang);
		bottonPanel2.add(Botton_anime_xia);
		bottonPanel2.add(Botton_anime_bigger);
		bottonPanel2.add(Botton_anime_explain);
		bottonPanel2.add(Botton_anime_fuzhi);
		bottonPanel2.add(Botton_anime_play);
		bottonPanel2.add(Botton_anime_stop);
		bottonPanel2.add(Botton_anime_smaller);
		
		Botton_anime_jia.setToolTipText("����һ���¶���");
		Botton_anime_jian.setToolTipText("ɾ��һ��ѡ�еĶ���");
		Botton_anime_shang.setToolTipText("ѡ�ж��������ƶ�");
		Botton_anime_xia.setToolTipText("ѡ�ж��������ƶ�");
		Botton_anime_explain.setToolTipText("��һ��ѡ�ж������ע��");
		Botton_anime_fuzhi.setToolTipText("�����Ѿ�ѡ�еĶ������¶����ᱻ���������·�");
		Botton_anime_play.setToolTipText("���Ŷ���");
		Botton_anime_stop.setToolTipText("ֹͣ����");
		
		Botton_anime_bigger.addActionListener(actionListener);
		Botton_anime_smaller.addActionListener(actionListener);

		Botton_frame_jia.registerKeyboardAction(actionListener, "Botton_frame_jia", KeyStroke.getKeyStroke(KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		Botton_frame_del.registerKeyboardAction(actionListener, "Botton_frame_del", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		Botton_frame_shang.registerKeyboardAction(actionListener, "Botton_frame_shang", KeyStroke.getKeyStroke(KeyEvent.VK_UP, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		Botton_frame_xia.registerKeyboardAction(actionListener, "Botton_frame_xia", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);

		Botton_anime_play.registerKeyboardAction(actionListener, "Botton_anime_play", KeyStroke.getKeyStroke(KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);

		Botton_anime_jia.addActionListener(actionListener);
		Botton_anime_jian.addActionListener(actionListener);
		Botton_anime_shang.addActionListener(actionListener);
		Botton_anime_xia.addActionListener(actionListener);
		Botton_anime_fuzhi.addActionListener(actionListener);
		Botton_anime_play.addActionListener(actionListener);
		Botton_anime_stop.addActionListener(actionListener);
		Botton_anime_explain.addActionListener(actionListener);
		middle_Jpanel.add(useframelist_scrollPane, BorderLayout.NORTH);
		middle_Jpanel.add(animelist_scrollPane, BorderLayout.CENTER);
		middle_Jpanel.add(bottonPanel2, BorderLayout.SOUTH);
		this.add(middle_Jpanel, BorderLayout.EAST);
//		this.add(middle_Jpanel, BorderLayout.CENTER);
		// �ұ߿������
		right_Jpanel.setPreferredSize(new Dimension(450, 100));
		small_Imagepanel.setPreferredSize(new Dimension(450, 350));
		BigImagePanel.setPreferredSize(new Dimension(450, 350));
		right_Jpanel.add(small_Imagepanel, BorderLayout.NORTH);
		right_Jpanel.add(BigImagePanel, BorderLayout.CENTER);
		this.add(right_Jpanel, BorderLayout.CENTER);
//		this.add(right_Jpanel, BorderLayout.EAST);
	}
}
