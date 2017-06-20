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
 * mod_panel 物块编辑画面
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
	JButton button_image_up = new JButton("上移");
	JButton button_image_down = new JButton("下移");
	JButton button_image_del = new JButton("删除图片");
	JButton button_LOAD = new JButton("读取图片");
	JButton button_BIG = new JButton("放大"); // 快捷键 =
	JButton button_SMALL = new JButton("缩小"); // 快捷键 -
	JButton button_NORMALSIZE = new JButton("原大");
	JButton button_DEL = new JButton("删除物块"); // 快捷键 Del


	JFormattedTextField displayTextField = new JFormattedTextField();
	JButton button_EXPLAN= new JButton("保存说明"); // 快捷键 Del
	
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
			// 读取资源
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
						// 更新列表
						SDef.image_listModel.clear();
						for (int i = 0; i < SDef.mTotalInfo.mBigImagelist.size(); i++) {
							BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(i);
							SDef.image_listModel.addElement(bii.getName());
						}
						ImageList.setSelectedIndex(SDef.mTotalInfo.mBigImagelist.size() - 1);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "读取资源出错，请检查是否格式有误", "出错", JOptionPane.ERROR_MESSAGE);
					}
					BigImagePanel.update_Srceen();
				}
			}
			// Image放大
			if (source == button_BIG) {

				SDef.mod_size++;
				if (SDef.mod_size > 8) {
					SDef.mod_size = 8;
				}

				temp_bfimage = null;
				int selectedIndex = ImageList.getSelectedIndex();
				BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(selectedIndex);
				temp_bfimage = new BufferedImage((int) (bii.mStaticBigImage.getWidth() * SDef.mod_size), (int) (bii.mStaticBigImage.getHeight() * SDef.mod_size), BufferedImage.TYPE_INT_ARGB);
				temp_bfimage.getGraphics().drawImage(bii.mBigImage, 0, 0, temp_bfimage.getWidth(), temp_bfimage.getHeight(), null); // 绘制缩小后的图
				bii.mBigImage = temp_bfimage;
				BigImagePanel.update_Srceen();
				System.gc();
			}
			// Image缩小
			if (source == button_SMALL) {
				SDef.mod_size--;
				if (SDef.mod_size < 1) {
					SDef.mod_size = 1;
				}
				temp_bfimage = null;
				int selectedIndex = ImageList.getSelectedIndex();
				BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(selectedIndex);
				temp_bfimage = new BufferedImage((int) (bii.mStaticBigImage.getWidth() * SDef.mod_size), (int) (bii.mStaticBigImage.getHeight() * SDef.mod_size), BufferedImage.TYPE_INT_ARGB);
				temp_bfimage.getGraphics().drawImage(bii.mBigImage, 0, 0, temp_bfimage.getWidth(), temp_bfimage.getHeight(), null); // 绘制缩小后的图
				bii.mBigImage = temp_bfimage;
				BigImagePanel.update_Srceen();
				System.gc();
			}

			// 还原图片
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
						JOptionPane.showMessageDialog(null, "您尚未选中一个物块", "出错", JOptionPane.ERROR_MESSAGE);
					}
					
					
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
			
			// 删除
			if (source == button_DEL) {
				int[] temp = new int[wordList.getSelectedIndices().length];
				temp = wordList.getSelectedIndices();

				// 检查是否有人使用
				String usedframe = "";
				boolean can_del = true;
				for (int j = 0; j < temp.length; j++) {
					usedframe = usedframe + SDef.mTotalInfo.hasModIDfromFramelist(temp[j]);
				
				}
				if (usedframe.length() > 0) {
					can_del = false;
					usedframe = "正在调用此物块的帧有：\n" + usedframe + "\n不能删除";
				}
				if (can_del == false) {
					JOptionPane.showMessageDialog(null, usedframe, "出错", JOptionPane.ERROR_MESSAGE);
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

					// 帧中的数据更新
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
		button_LOAD.setToolTipText("读取资源大图");
		buttonPanel.add(button_BIG);
		button_BIG.setToolTipText("放大图片，注意不要放大过多导致内存占用超过限制");
		buttonPanel.add(button_SMALL);
		button_SMALL.setToolTipText("缩小图片");
		buttonPanel.add(button_NORMALSIZE);
		button_NORMALSIZE.setToolTipText("显示原始大小");
		buttonPanel.add(button_DEL);
		button_DEL.setToolTipText("删除已选中的图片");
		buttonPanel.add(button_EXPLAN);
		button_EXPLAN.setToolTipText("给物块添加解释");
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
