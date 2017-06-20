package com.ikags.animeeditor2.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import com.ikags.animeeditor2.SDef;
import com.ikags.animeeditor2.info.BigImageInfo;
import com.ikags.animeeditor2.info.ImageModInfo;

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
 * mod_panel ���༭����
 */

public class ModPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	ModImage BigImagePanel = new ModImage();
	static ModOneImage smalImagePanel = new ModOneImage();
	static JList wordList = new JList(SDef.mod_listModel);
	JScrollPane scrollPane = new JScrollPane(wordList);
	static JList ImageList = new JList(SDef.image_listModel);
	JScrollPane scrollPane2 = new JScrollPane(ImageList);

	JPanel rightPanel = new JPanel(new BorderLayout());
	JPanel leftPanel = new JPanel(new BorderLayout());
	JPanel buttonPanel = new JPanel(new GridLayout(1, 8));
	JPanel buttonPanel2 = new JPanel(new GridLayout(1, 3));
	JButton button_image_up = new JButton("����");
	JButton button_image_down = new JButton("����");
	JButton button_image_del = new JButton("ɾ��ͼƬ");
	JButton button_LOAD = new JButton("��ȡͼƬ");
	JButton button_BIG = new JButton("�Ŵ�"); // ��ݼ� =
	JButton button_SMALL = new JButton("��С"); // ��ݼ� -
	JButton button_NORMALSIZE = new JButton("ԭ��");
	JButton button_DEL = new JButton("ɾ�����"); // ��ݼ� Del


	JFormattedTextField displayTextField = new JFormattedTextField();
	JButton button_EXPLAN= new JButton("����˵��"); // ��ݼ� Del
	
	AffineTransform transform;
	BufferedImage temp_bfimage;

	FileFilter flefilter = new FileFilter() {
		public boolean accept(File f) {
			return f.getName().toLowerCase().endsWith(".png") || f.isDirectory();
		}

		public String getDescription() {
			return "PNG Files";
		}
	};

	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			Object source = actionEvent.getSource();
			System.gc();
			// ��ȡ��Դ
			if (source == button_LOAD) {
				JFileChooser c = new JFileChooser();
				c.setFileFilter(flefilter);
				int rVal = c.showOpenDialog(ModPanel.this);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					String mame = c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName();
					System.out.println(mame);
					File pngfile = new File(mame);
					try {

						BigImageInfo bif = new BigImageInfo();
						bif.mPath = mame;

						bif.mBigImage = ImageIO.read(pngfile);
						// TODO
						// ImageIO.write(bif.mBigImage, "png", pngfile);
						bif.mStaticBigImage = bif.mBigImage;
						SDef.mTotalInfo.addBigImageInfo(bif);
						// �����б�
						SDef.image_listModel.clear();
						for (int i = 0; i < SDef.mTotalInfo.mBigImagelist.size(); i++) {
							BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(i);
							SDef.image_listModel.addElement(bii.getName());
						}
						ImageList.setSelectedIndex(SDef.mTotalInfo.mBigImagelist.size() - 1);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "��ȡ��Դ���������Ƿ��ʽ����", "����", JOptionPane.ERROR_MESSAGE);
					}
					BigImagePanel.update_Srceen();
				}
			}
			// Image�Ŵ�
			if (source == button_BIG) {

				SDef.mod_size++;
				if (SDef.mod_size > 8) {
					SDef.mod_size = 8;
				}

				temp_bfimage = null;
				int selectedIndex = ImageList.getSelectedIndex();
				BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(selectedIndex);
				temp_bfimage = new BufferedImage((int) (bii.mStaticBigImage.getWidth() * SDef.mod_size), (int) (bii.mStaticBigImage.getHeight() * SDef.mod_size), BufferedImage.TYPE_INT_ARGB);
				temp_bfimage.getGraphics().drawImage(bii.mBigImage, 0, 0, temp_bfimage.getWidth(), temp_bfimage.getHeight(), null); // ������С���ͼ
				bii.mBigImage = temp_bfimage;
				BigImagePanel.update_Srceen();
				System.gc();
			}
			// Image��С
			if (source == button_SMALL) {
				SDef.mod_size--;
				if (SDef.mod_size < 1) {
					SDef.mod_size = 1;
				}
				temp_bfimage = null;
				int selectedIndex = ImageList.getSelectedIndex();
				BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(selectedIndex);
				temp_bfimage = new BufferedImage((int) (bii.mStaticBigImage.getWidth() * SDef.mod_size), (int) (bii.mStaticBigImage.getHeight() * SDef.mod_size), BufferedImage.TYPE_INT_ARGB);
				temp_bfimage.getGraphics().drawImage(bii.mBigImage, 0, 0, temp_bfimage.getWidth(), temp_bfimage.getHeight(), null); // ������С���ͼ
				bii.mBigImage = temp_bfimage;
				BigImagePanel.update_Srceen();
				System.gc();
			}

			// ��ԭͼƬ
			if (source == button_NORMALSIZE) {
				SDef.mod_size = 1;

				int selectedIndex = ImageList.getSelectedIndex();
				BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(selectedIndex);
				bii.mBigImage = bii.mStaticBigImage;
				BigImagePanel.update_Srceen();
			}

			
			if(source == button_EXPLAN){
				String mtext=displayTextField.getText();
				if(mtext!=null){
					int[] temp = new int[wordList.getSelectedIndices().length];
					temp = wordList.getSelectedIndices();
					
					try{
					if(temp!=null&&temp.length>0&&temp[0]>-1){
						ImageModInfo imc = SDef.mTotalInfo.mImageModlist.elementAt(temp[0]);
						imc.mInfo=mtext;
						SDef.mod_listModel.clear();
						for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
							ImageModInfo im = SDef.mTotalInfo.mImageModlist.elementAt(i);
							SDef.mod_listModel.addElement(im.getName());
						}
						wordList.setSelectedIndex(temp[0]);
					}else{
						JOptionPane.showMessageDialog(null, "����δѡ��һ�����", "����", JOptionPane.ERROR_MESSAGE);
					}
					
					
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
			
			// ɾ��
			if (source == button_DEL) {
				int[] temp = new int[wordList.getSelectedIndices().length];
				temp = wordList.getSelectedIndices();

				// ����Ƿ�����ʹ��
				String usedframe = "";
				boolean can_del = true;
				for (int j = 0; j < temp.length; j++) {
					usedframe = usedframe + SDef.mTotalInfo.hasModIDfromFramelist(temp[j]);
				
				}
				if (usedframe.length() > 0) {
					can_del = false;
					usedframe = "���ڵ��ô�����֡�У�\n" + usedframe + "\n����ɾ��";
				}
				if (can_del == false) {
					JOptionPane.showMessageDialog(null, usedframe, "����", JOptionPane.ERROR_MESSAGE);
				} else {

					for (int j = 0; j < temp.length; j++) {
						SDef.mTotalInfo.removeImageModInfo(temp[j]);
					}
					SDef.mod_listModel.clear();
					for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
						ImageModInfo im = SDef.mTotalInfo.mImageModlist.elementAt(i);
						SDef.mod_listModel.addElement(im.getName());
					}
					wordList.setSelectedIndex(temp[0]);

					// ֡�е����ݸ���
					for (int j = 0; j < temp.length; j++) {
						SDef.mTotalInfo.removeImageModFromFrameList(temp[j]);
					}
				}
			}

			if (source == button_image_del) {

				int ImageSI = ImageList.getSelectedIndex();

				SDef.mTotalInfo.mBigImagelist.remove(ImageSI);

				SDef.image_listModel.clear();
				for (int i = 0; i < SDef.mTotalInfo.mBigImagelist.size(); i++) {
					BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(i);
					File file = new File(bii.mPath);
					SDef.image_listModel.addElement(file.getName());
				}
				ImageList.setSelectedIndex(SDef.mTotalInfo.mBigImagelist.size() - 1);

			}
			
			
		}
	};

	public ModPanel() {

		transform = new AffineTransform();
		wordList.setDragEnabled(true);
		setLayout(new BorderLayout());
		scrollPane.setPreferredSize(new Dimension(200, 100));
		scrollPane2.setPreferredSize(new Dimension(200, 100));
		buttonPanel.setPreferredSize(new Dimension(100, 25));
		buttonPanel2.setPreferredSize(new Dimension(100, 25));
		smalImagePanel.setPreferredSize(new Dimension(200, 200));
		buttonPanel.setLayout(new GridLayout(1, 8));
		buttonPanel.add(button_LOAD);
		button_LOAD.setToolTipText("��ȡ��Դ��ͼ");
		buttonPanel.add(button_BIG);
		button_BIG.setToolTipText("�Ŵ�ͼƬ��ע�ⲻҪ�Ŵ���ർ���ڴ�ռ�ó�������");
		buttonPanel.add(button_SMALL);
		button_SMALL.setToolTipText("��СͼƬ");
		buttonPanel.add(button_NORMALSIZE);
		button_NORMALSIZE.setToolTipText("��ʾԭʼ��С");
		buttonPanel.add(button_DEL);
		button_DEL.setToolTipText("ɾ����ѡ�е�ͼƬ");
		buttonPanel.add(button_EXPLAN);
		button_EXPLAN.setToolTipText("�������ӽ���");
		button_LOAD.registerKeyboardAction(actionListener, "button_LOAD", KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		button_BIG.registerKeyboardAction(actionListener, "button_BIG", KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		button_SMALL.registerKeyboardAction(actionListener, "button_SMALL", KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		button_DEL.registerKeyboardAction(actionListener, "button_ENTER", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		button_LOAD.addActionListener(actionListener);
		button_BIG.addActionListener(actionListener);
		button_SMALL.addActionListener(actionListener);
		button_NORMALSIZE.addActionListener(actionListener);
		button_DEL.addActionListener(actionListener);
		button_EXPLAN.addActionListener(actionListener);
		// buttonPanel2.add(button_image_up);
		// buttonPanel2.add(button_image_down);
		// buttonPanel2.add(button_image_del);
		// button_image_up.addActionListener(actionListener);
		// button_image_down.addActionListener(actionListener);
		// button_image_del.addActionListener(actionListener);

		leftPanel.add(buttonPanel2, BorderLayout.SOUTH);
		leftPanel.add(scrollPane2, BorderLayout.CENTER);

		rightPanel.add(smalImagePanel, BorderLayout.NORTH);
		rightPanel.add(scrollPane, BorderLayout.CENTER);
		rightPanel.add(displayTextField, BorderLayout.SOUTH);

		
		this.add(leftPanel, BorderLayout.WEST);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(BigImagePanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
	}
}
