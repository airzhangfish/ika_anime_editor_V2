package com.ikags.animeeditor2;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.ikags.animeeditor2.info.AnimeInfo;
import com.ikags.animeeditor2.info.BigImageInfo;
import com.ikags.animeeditor2.info.FrameInfo;
import com.ikags.animeeditor2.info.ImageModInfo;
import com.ikags.animeeditor2.info.ModInfo;
import com.ikags.animeeditor2.util.ConvertUtil;
import com.ikags.animeeditor2.util.DrawUtil;
import com.ikags.animeeditor2.util.gif.AnimatedGifEncoder;
import com.ikags.animeeditor2.view.AnimePanel;
import com.ikags.animeeditor2.view.FrameImage;
import com.ikags.animeeditor2.view.FramePanel;
import com.ikags.animeeditor2.view.ModPanel;
import com.ikags.animeeditor2.view.MyUpdateThread;
import com.ikags.animeeditor2.view.setting.ColorSelect1;
import com.ikags.animeeditor2.view.setting.ColorSelect2;
import com.ikags.animeeditor2.view.setting.ColorSelect3;
import com.ikags.animeeditor2.view.setting.DelaySelect;
import com.ikags.animeeditor2.view.setting.ScreenSelect;
import com.ikags.util.CommonUtil;

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
 * last updata 2007-12-24
 * </p>
 * 主类
 */

public class IkaAnimeEditor extends JFrame implements ActionListener {

	String mVerion="-Version 2.0.6 in 2014-03-14";
	String titlename = "Ika 动画编辑器    "+mVerion;
	// 关于
	private String aboutStr = " IKA 动画编辑器\n  " + "Creator by airzhangfish \n " + mVerion+"\n " + " E-mail:52643971@qq.com\n http://www.ikags.com/";
	// 帮助
	private String helpStr = "IKA 动画编辑器   使用帮助\n" + "快捷键说明：\n" + "\n" + "物块编辑：\n" + "读取图片            F2\n" + "放大                    =  \n" + "缩小                    -\n" + "删除                   Delete\n" + "\n" + "帧编辑：\n" + "移动                   上下左右\n" + "删除物块            Delete\n"
			+ "图片切换            鼠标中键\n" + "\n" + "动画编辑：\n" + "动作中帧删除       Delete\n" + "动画播放             C\n" + "动画暂停             V\n" + "动画中加入帧       A\n";

	private static final long serialVersionUID = 1L;
	private JTabbedPane jtp;
	private JMenuBar jMenuBar1 = new JMenuBar();
	private JMenu jMenuFile = new JMenu("文件(F)");

	// private JMenuItem jMenuFileOpenika = new JMenuItem("读取ika文件...");
	// private JMenuItem jMenuFileSaveika = new JMenuItem("保存为ika文件...");
	private JMenuItem jMenuFileSaveika = new JMenuItem("导出ika文件..                  (Ctrl+E)");
	private JMenuItem jMenuFileSaveImage = new JMenuItem("导出图片..                    (Ctrl+P)");
	private JMenuItem jMenuFileSavepack = new JMenuItem("打包图片..                    ");
	private JMenuItem jMenuFileOpenxml = new JMenuItem("读取xml文件..              (Ctrl+O)");
	private JMenuItem jMenuFileSavexml = new JMenuItem("保存为xml文件..             (Ctrl+S)");
	private JMenuItem jMenuFileSaveGIF = new JMenuItem("指定动画保存为GIF    (Ctrl+G)");
	private JMenuItem jMenuFileSavePNG = new JMenuItem("指定帧保存为PNG    (Ctrl+G)");
	private JMenuItem jMenuFileExit = new JMenuItem("退出           (Ctrl+X)");

	private JMenu jMenuOpinion = new JMenu("设置(S)");
	private JMenuItem jMenuOpinionBGColor = new JMenuItem("设置背景色");
	private JMenuItem jMenuOpinionBoxColor = new JMenuItem("设置栅格颜色");
	private JMenuItem jMenuOpinionlineColor = new JMenuItem("设置轴颜色");
	private JMenuItem jMenuOpinionScreen = new JMenuItem("设置手机屏幕大小      (Ctrl+C)");
	private JMenuItem jMenuOpinionFrame = new JMenuItem("设置帧延迟                   (Ctrl+D)");

	private JMenu jMenuHelp = new JMenu("帮助(H)");
	private JMenuItem jMenuHelpAbout = new JMenuItem("关于");
	private JMenuItem jMenuHelpHelp = new JMenuItem("帮助       (F6)");
	private JMenuItem jMenuHelpHomepage = new JMenuItem("开发者主页");
	ModPanel mod_Panelx = new ModPanel();
	FramePanel frame_panelx = new FramePanel();
	AnimePanel anime_panelx = new AnimePanel();
	JPanel frame_Panel = new JPanel();
	JPanel anime_Panel = new JPanel();
	MyUpdateThread mthread;

	public void actionPerformed(ActionEvent actionEvent) {
		Object source = actionEvent.getSource();
		// 保存xml数据
		if (source == jMenuFileSavexml) {
			FileDialog xs = new FileDialog(this, "save xml file", FileDialog.SAVE);
			xs.setFile("*.*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				savexml(lastDir + f);
			}
		}
		// 读取xml数据
		if (source == jMenuFileOpenxml) {
			FileDialog xs = new FileDialog(this, "load xml file", FileDialog.LOAD);
			xs.setFile("*.xml*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				// SDef.reset_new();
				new XMLread(lastDir + f);
			}
		}

		// 保存ika文件
		if (source == jMenuFileSaveika) {
			FileDialog xs = new FileDialog(this, "save ika file", FileDialog.SAVE);
			xs.setFile("*.*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				savedata(lastDir + f);
			}
		}
		// 保存图片文件
		if (source == jMenuFileSaveImage) {
			FileDialog xs = new FileDialog(this, "save Image file", FileDialog.SAVE);
			xs.setFile("*.*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				saveImage(lastDir);
			}
		}

		// 保存图片打包文件
		if (source == jMenuFileSavepack) {

			if (canpack == true) {
				getPNG(pngfiles);
				output_Res(pngfiles);
			} else {
				JOptionPane.showMessageDialog(this, "请先导出PNG小图片", "打包错误提示", JOptionPane.ERROR_MESSAGE);
			}
		}

		// 保存图片文件
		if (source == jMenuFileSaveGIF) {
			FileDialog xs = new FileDialog(this, "save GIF file", FileDialog.SAVE);
			xs.setFile("*.*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				saveGIF(lastDir + f);
			}
		}
		
		// 保存图片文件
		if (source == jMenuFileSavePNG) {
			FileDialog xs = new FileDialog(this, "save PNG file", FileDialog.SAVE);
			xs.setFile("*.*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				savePNG(lastDir + f);
			}
		}

		// 读取ika文件
		// if (source == jMenuFileOpenika) {
		// FileDialog xs = new FileDialog(this, "load ika file", FileDialog.LOAD);
		// xs.setFile("*.ika*");
		// xs.show();
		// String f = xs.getFile();
		// String lastDir = xs.getDirectory();
		// if (f != null) {
		// opendata(lastDir + f);
		// }
		// }

		// 帮助
		if (source == jMenuHelpHelp) {
			JOptionPane.showMessageDialog(this, helpStr, "帮助", JOptionPane.INFORMATION_MESSAGE);
		}
		// 关于
		if (source == jMenuHelpAbout) {
			JOptionPane.showMessageDialog(this, aboutStr, "关于", JOptionPane.INFORMATION_MESSAGE);
		}
		// 软件退出
		if (source == jMenuFileExit) {
			System.exit(0);
		}
		// 打开作者主页
		if (source == jMenuHelpHomepage) {
			CommonUtil.browserURL("http://www.ikags.com");
		}
		// 几个颜色设置
		if (source == jMenuOpinionBGColor) { // 背景色设定
			ColorSelect1 xxx = new ColorSelect1();
			xxx.setVisible(true);
		}
		if (source == jMenuOpinionBoxColor) { // 栅格色设定
			ColorSelect2 xxx = new ColorSelect2();
			xxx.setVisible(true);
		}
		if (source == jMenuOpinionlineColor) { // 轴颜色设定
			ColorSelect3 xxx = new ColorSelect3();
			xxx.setVisible(true);
		}
		if (source == jMenuOpinionScreen) { // 屏幕大小设定
			ScreenSelect xxx = new ScreenSelect();
			xxx.setVisible(true);
		}
		if (source == jMenuOpinionFrame) { // 延迟设定
			DelaySelect xxx = new DelaySelect();
			xxx.setVisible(true);
		}

	}
	public void getPNG(File[] file) {
		total_PNG_length = 0;
		PNGlong = 0;
		for (int i = 0; i < total_number; i++) {
			total_PNG_length = total_PNG_length + file[i].length();
		}
		PNGlong = (int) total_PNG_length + 4 * (total_number) + 4;
		PNGbox = new byte[PNGlong];
		int PNGBOXID = 0;
//		PNGbox[PNGBOXID] = (byte) ((total_number) >> 8);
//		PNGBOXID++;
//		PNGbox[PNGBOXID] = (byte) ((total_number) - (PNGbox[PNGBOXID] << 8));
//		PNGBOXID++;
//		
		byte[] bytes_total=ConvertUtil.intToByte(total_number);
		PNGbox[PNGBOXID] = bytes_total[0];
		PNGBOXID++;
		PNGbox[PNGBOXID] = bytes_total[1];
		PNGBOXID++;
		PNGbox[PNGBOXID] = bytes_total[2];
		PNGBOXID++;
		PNGbox[PNGBOXID] = bytes_total[3];
		PNGBOXID++;
		
		
		for (int i = 0; i < total_number; i++) {

			byte[] bytespng=ConvertUtil.intToByte((int) file[i].length() );
			
			PNGbox[PNGBOXID] = bytespng[0];
			PNGBOXID++;
			PNGbox[PNGBOXID] = bytespng[1];
			PNGBOXID++;
			PNGbox[PNGBOXID] =bytespng[2];
			PNGBOXID++;
			PNGbox[PNGBOXID] = bytespng[3];
			PNGBOXID++;
			try {
				FileInputStream fo = new FileInputStream(file[i]);
				byte[] mx = new byte[fo.available()];
				fo.read(mx);
				for (int j = 0; j < mx.length; j++) {
					PNGbox[PNGBOXID] = mx[j];
					PNGBOXID++;
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "打包失败", "出错", JOptionPane.ERROR_MESSAGE);
				System.out.println("read " + file[i] + " error");
			}
		}
	}

	static byte[] PNGbox;
	static int PNGlong = 0;
	static long total_PNG_length = 0;

	public void output_Res(File[] file) {
		File pakFile = CommonUtil.createFile(file[0].getParent() + "\\imgpak.bin");
		try {
			FileOutputStream fo = new FileOutputStream(pakFile);
			fo.write(PNGbox);
			fo.close();
			System.out.println("pak over");

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "打包失败", "出错", JOptionPane.ERROR_MESSAGE);
			System.out.println("output_Res error");
		}
	}

	public static File[] pngfiles = new File[1000];
	public static boolean canpack = false;
	public void saveGIF(String path) {
		try {
			BufferedImage bfimg = new BufferedImage(SDef.MO_W, SDef.MO_H, BufferedImage.TYPE_INT_ARGB);
			AnimatedGifEncoder gifs = new AnimatedGifEncoder();
			gifs.setRepeat(0);
			gifs.start(path + ".gif");
			gifs.setQuality(1); // 最好质量
			int animeindex = AnimePanel.animelist.getSelectedIndex();
			AnimeInfo anime = SDef.mTotalInfo.mAnimelist.elementAt(animeindex);

			System.out.println("gif-frame:" + anime.mFrameIDlist.size());
			for (int j = 0; j < anime.mFrameIDlist.size(); j++) {
				Graphics gg = bfimg.getGraphics();
				gg.setColor(new Color(0x000000));
				gg.fillRect(0, 0, bfimg.getWidth(), bfimg.getHeight());

				int frameid = anime.mFrameIDlist.elementAt(j);
				FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(frameid);

				for (int i = 0; i < fi.mModlist.size(); i++) {
					ModInfo mod = fi.mModlist.elementAt(i);
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(mod.mImageModID);
					DrawUtil.drawAniPart20(imi.mImage, mod.mX - SDef.MO_X, mod.mY - SDef.MO_Y, mod.mRotate,mod.mScale, gg);
				}
				gifs.setDelay(SDef.SYSTEM_DELAY);
				gifs.addFrame(bfimg);
			}
			gifs.finish();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "保存GIF出错", "出错", JOptionPane.ERROR_MESSAGE);
		}

	}


	//保存单帧图片
	public void savePNG(String path) {
		try {
//			BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//			tag.getGraphics().drawImage(bimage, -x, -y, bimage.getWidth(), bimage.getHeight(), null);
			
			BufferedImage bfimg = new BufferedImage(SDef.MO_W, SDef.MO_H, BufferedImage.TYPE_INT_ARGB);
			int frameindex = FramePanel.wordList.getSelectedIndex();
			FrameInfo frame = SDef.mTotalInfo.mFramelist.elementAt(frameindex);

			System.out.println("frame index:"+ frameindex+",frame size="+ frame.mModlist.size()+",PATH="+path);
		
				Graphics gg = bfimg.getGraphics();
//				gg.setColor(new Color(0x00ffffff));
//				gg.fillRect(0, 0, bfimg.getWidth(), bfimg.getHeight());

				int frameid = frameindex;
				FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(frameid);

				for (int i = 0; i < fi.mModlist.size(); i++) {
					ModInfo mod = fi.mModlist.elementAt(i);
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(mod.mImageModID);
					DrawUtil.drawAniPart20(imi.mImage, mod.mX - SDef.MO_X, mod.mY - SDef.MO_Y, mod.mRotate,mod.mScale, gg);
				}

				File pngfile=new File(path+".png");
			    ImageIO.write(bfimg, "png", pngfile);
				
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "保存PNG出错", "出错", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	
	// 保存图片
	public static int total_number = 0;
	public void saveImage(String path) {
		total_number = 0;
		File txtFile = CommonUtil.createFile(path + "png.bat");
		String outString = "";
		for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
			ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(i);
			if (imi.mImage == null) {
				break;
			} else {

				pngfiles[i] = CommonUtil.createFile(path + "Mod_" + i + ".png");
				try {
					outString = outString + "PngMate -colors 128 " + "Mod_" + i + ".png " + "Mod_" + i + ".png\n\r";
					ImageIO.write(imi.mImage, "png", pngfiles[i]);
					total_number++;
				} catch (Exception ex) {
					System.out.println("png save error");
					JOptionPane.showMessageDialog(this, "图片保存出错", "出错", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		System.out.println("png save over");

		try {
			FileOutputStream fo = new FileOutputStream(txtFile);
			fo.write(outString.getBytes("GB2312"));
			fo.close();
			canpack = true;
		} catch (Exception ex) {
			System.out.println("png txt error");
			JOptionPane.showMessageDialog(this, "转换文件保存出错", "出错", JOptionPane.ERROR_MESSAGE);
		}
		System.out.println("png txt over");
	}

	// 保存xml
	String output = "";

	public void savexml(String path) {
		int modlength = 0;
		output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
		output = output + "<ikaanime>\r\n";
		// 输出Image地址
		for (int i = 0; i < SDef.mTotalInfo.mBigImagelist.size(); i++) {
			BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.get(i);
			output = output + "<bigimage path=\"" + bii.mPath + "\" />\r\n";
		}

		// 输出物块

		for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
			ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(i);
			output = output + "<imagemod" + " x=\"" + imi.mX + "\" y=\"" + imi.mY + "\" width=\"" + imi.mWidth + "\" height=\"" + imi.mHeight + "\" use=\"" + imi.mUsed +"\" info=\"" + imi.mInfo + "\" image=\"" + imi.mBigImageID + "\" />\r\n";
		}
		// 输出帧
		for (int i = 0; i < SDef.mTotalInfo.mFramelist.size(); i++) {
			FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(i);
			output = output + "<frame info=\"" + fi.mInfo + "\">\r\n";
			for (int j = 0; j < fi.mModlist.size(); j++) {
				ModInfo mod = fi.mModlist.elementAt(j);
				output = output + "   <mod id=\"" + mod.mImageModID + "\" x=\"" + mod.mX + "\" y=\"" + mod.mY + "\" rtype=\"" + mod.mRotateType + "\" rotate=\"" + mod.mRotate + "\" scale=\"" + mod.mScale + "\" info=\"" + mod.mInfo + "\" />\r\n";
			}
			output = output + "</frame>\r\n";
		}
		// 输出动画
		for (int i = 0; i < SDef.mTotalInfo.mAnimelist.size(); i++) {
			AnimeInfo ani = SDef.mTotalInfo.mAnimelist.elementAt(i);
			output = output + "<anime info=\"" + ani.mInfo + "\">\r\n";
			for (int j = 0; j < ani.mFrameIDlist.size(); j++) {
				int myint = ani.mFrameIDlist.get(j).intValue();
				output = output + "   <frame id=\"" + myint + "\" />\r\n";
			}
			output = output + "</anime>\r\n";
		}
		output = output + "</ikaanime>\r\n";
		if (path.substring(path.length() - 4, path.length()).equals(".xml")) {
			path = path.substring(0, path.length() - 4);
		}
		File pakFile = CommonUtil.createFile(path + ".xml");
		try {
			FileOutputStream fo = new FileOutputStream(pakFile);
			fo.write(output.getBytes("UTF-8"));
			fo.close();
		} catch (Exception ex) {
			System.out.println("save xml error");
			JOptionPane.showMessageDialog(this, "xml文件保存出错", "出错", JOptionPane.ERROR_MESSAGE);
		}
	}

	// 保存数据
	public void savedata(String path) {
		
		try{
		
	 int[] outputintdata;
	 
	 //帧存入数组
	 int[][][] framebox = new int[SDef.mTotalInfo.mFramelist.size()][][];
	 for (int i = 0; i < SDef.mTotalInfo.mFramelist.size(); i++) {
		 FrameInfo fi=SDef.mTotalInfo.mFramelist.elementAt(i);
		 framebox[i]=new int[fi.mModlist.size()][6];
	 for (int j = 0; j<fi.mModlist.size(); j++) {
		 ModInfo mi= fi.mModlist.elementAt(j);
		 framebox[i][j][0]=mi.mImageModID;
		 framebox[i][j][1]=mi.mX;
		 framebox[i][j][2]=mi.mY;
		 framebox[i][j][3]=mi.mRotate;
		 framebox[i][j][4]=(int)(mi.mScale*10);
		 framebox[i][j][5]=mi.mRotateType;
	 }
	 }
	 
	 //动画存入数组
	 int[][] animebox = new int[SDef.mTotalInfo.mAnimelist.size()][];
	 for (int i = 0; i < SDef.mTotalInfo.mAnimelist.size(); i++) {
		 AnimeInfo fi=SDef.mTotalInfo.mAnimelist.elementAt(i);
		 animebox[i]=new int[fi.mFrameIDlist.size()];
	 for (int j = 0; j<fi.mFrameIDlist.size(); j++) {
		 int frameid=fi.mFrameIDlist.elementAt(j);
		 animebox[i][j]=frameid;
	 }
	 }
	 

	 //统计长度
	 int totallength=0;
	 totallength=totallength+1;  // 1位标记读取长度
	 for (int i = 0; i < framebox.length; i++) {
		 totallength=totallength+1;  // 1位标记读取长度
	 for (int j = 0; j<framebox[i].length; j++) {
		 totallength=totallength+1+framebox[i][j].length;  // 1位标记读取长度+数据信息
	 }
	 }
	 
	 totallength=totallength+1;  // 1位标记读取长度
	 for (int i = 0; i < animebox.length; i++) {
		 totallength=totallength+1;  // 1位标记读取长度
		 for (int j = 0; j<animebox[i].length; j++) {
			 totallength=totallength+1+animebox[i].length;  //1位标记长度+数据信息
		 }
		 }
	 
	 
//	 public static void arraycopy(Object src,int srcPos,Object dest,int destPos, int length)
	 // 载入int型
	 outputintdata = new int[totallength];
	 int pos=0;
	 outputintdata[pos]=framebox.length;
	 pos++;
	 for (int i = 0; i < framebox.length; i++) {
		 outputintdata[pos]=framebox[i].length;
		 pos++;
	 for (int j = 0; j<framebox[i].length; j++) {
		 outputintdata[pos]=framebox[i][j].length;
		 pos++;
		 System.arraycopy(framebox[i][j], 0, outputintdata, pos, framebox[i][j].length);
		 pos=pos+framebox[i][j].length;
	 }
	 }
	 
	 outputintdata[pos]=animebox.length;
	 pos++;
	 for (int i = 0; i < animebox.length; i++) {
		 outputintdata[pos]=animebox[i].length;
		 pos++;
		 System.arraycopy(animebox[i], 0, outputintdata, pos, animebox[i].length);
		 pos=pos+animebox[i].length;
		 
		 String str="";
		 for(int j=0;j<animebox[i].length;j++){
			 str=str+animebox[i][j]+",";
		 }
		 System.out.println(i+",anime size="+animebox[i].length+" ,list="+str);
		 }
	 
	 
	 
	 //转化成byte
	
	 byte[] outputdata = new byte[3+outputintdata.length * 2];
		outputdata[0] = 0x49;
		outputdata[1] = 0x4B;
		outputdata[2] = 0x41;
		pos=3;

	 for (int i = 0; i < outputintdata.length; i++) {
		 byte[] bytes=ConvertUtil.shortToByte((short)outputintdata[i]);
		 System.arraycopy(bytes, 0, outputdata, pos, bytes.length);
		 pos=pos+bytes.length;
	 }
	 

	 File pakFile = CommonUtil.createFile(path + ".ika");
	 try {
	 FileOutputStream fo = new FileOutputStream(pakFile);
	 fo.write(outputdata);
	 fo.close();
	 } catch (Exception ex) {
	 System.out.println("save ika error");
	 JOptionPane.showMessageDialog(this, "ika文件保存出错", "出错", JOptionPane.ERROR_MESSAGE);
	 }
		}catch(Exception ex){
			 JOptionPane.showMessageDialog(this, "ika文件保存出错,请查看是否存在空帧或者空动画的情况.请修复后再重新导出文件", "出错", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}

	}

	public IkaAnimeEditor() {

		this.setSize(800, 600); // 将窗体的大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true); // 窗体
		this.setTitle(titlename); // 设置标题

		enableInputMethods(true);

		// menu菜单的选项
		// file
		// jMenuFileOpenika.registerKeyboardAction(this, "jMenuFileOpenika", KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0, false),
		// JComponent.WHEN_IN_FOCUSED_WINDOW);
		jMenuFileSaveika.registerKeyboardAction(this, "jMenuFileSaveika", KeyStroke.getKeyStroke(KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		jMenuFileOpenxml.registerKeyboardAction(this, "jMenuFileOpenxml", KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		jMenuFileSavexml.registerKeyboardAction(this, "jMenuFileSavexml", KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		jMenuFileExit.registerKeyboardAction(this, "jMenuFileExit", KeyStroke.getKeyStroke(KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		jMenuFileSaveImage.registerKeyboardAction(this, "jMenuFileSaveImage", KeyStroke.getKeyStroke(KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		jMenuFileSaveGIF.registerKeyboardAction(this, "jMenuFileSaveGIF", KeyStroke.getKeyStroke(KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);

		jMenuOpinionScreen.registerKeyboardAction(this, "jMenuOpinionScreen", KeyStroke.getKeyStroke(KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		jMenuOpinionFrame.registerKeyboardAction(this, "jMenuOpinionFrame", KeyStroke.getKeyStroke(KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);

		jMenuHelpHelp.registerKeyboardAction(this, "jMenuHelpHelp", KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
		jMenuFile.add(jMenuFileOpenxml);
		jMenuFileOpenxml.setToolTipText("打开一个专用的xml文件");

		jMenuFile.add(jMenuFileSavexml);
		jMenuFileSavexml.setToolTipText("保存为专用的xml文件");
		// jMenuFile.add(jMenuFileOpenika);
		jMenuFile.add(jMenuFileSaveika);
		jMenuFileSaveika.setToolTipText("保存为二进制文件");
		jMenuFile.add(jMenuFileSaveImage);
		jMenuFile.add(jMenuFileSavepack);
		jMenuFileSaveImage.setToolTipText("导出切割的小物块图像");
		jMenuFile.add(jMenuFileSavePNG);
		jMenuFileSavePNG.setToolTipText("选定一个帧，导出其为PNG");
		jMenuFile.add(jMenuFileSaveGIF);
		jMenuFileSaveGIF.setToolTipText("选定一个动画文件，导出其为GIF");
		jMenuFile.add(jMenuFileExit);
		jMenuFileExit.setToolTipText("退出");
		jMenuFileSavepack.addActionListener(this);

		jMenuFileSaveGIF.addActionListener(this);
		jMenuFileSavePNG.addActionListener(this);
		jMenuFileOpenxml.addActionListener(this);
		jMenuFileSavexml.addActionListener(this);
		// jMenuFileOpenika.addActionListener(this);
		jMenuFileSaveImage.addActionListener(this);
		jMenuFileSaveika.addActionListener(this);
		jMenuFileExit.addActionListener(this);

		// 设置
		jMenuOpinion.add(jMenuOpinionBGColor);
		jMenuOpinionBGColor.setToolTipText("设置编辑面板的背景颜色");
		jMenuOpinion.add(jMenuOpinionBoxColor);
		jMenuOpinionBoxColor.setToolTipText("设置网格线的颜色");
		jMenuOpinion.add(jMenuOpinionlineColor);
		jMenuOpinionlineColor.setToolTipText("设置轴线的颜色");
		jMenuOpinion.add(jMenuOpinionScreen);
		jMenuOpinionScreen.setToolTipText("设置手机屏幕大小");
		jMenuOpinion.add(jMenuOpinionFrame);
		jMenuOpinionFrame.setToolTipText("设置动画播放的每帧速度");
		jMenuOpinionBGColor.addActionListener(this);
		jMenuOpinionBoxColor.addActionListener(this);
		jMenuOpinionlineColor.addActionListener(this);
		jMenuOpinionScreen.addActionListener(this);
		jMenuOpinionFrame.addActionListener(this);

		// help
		jMenuHelp.add(jMenuHelpHelp);
		jMenuOpinionFrame.setToolTipText("Ika动画编辑器使用帮助");
		jMenuHelp.add(jMenuHelpAbout);
		jMenuFile.setMnemonic('F');
		jMenuOpinion.setMnemonic('S');
		jMenuHelp.setMnemonic('H');
		jMenuOpinionFrame.setToolTipText("Ika动画编辑器关于");
		jMenuHelpHelp.addActionListener(this);
		jMenuHelpAbout.addActionListener(this);
		jMenuHelp.add(jMenuHelpHomepage);
		jMenuHelpHomepage.addActionListener(this);
		// 总工具栏

		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuOpinion);
		jMenuBar1.add(jMenuHelp);
		this.setJMenuBar(jMenuBar1);

		Container contents = getContentPane();
		jtp = new JTabbedPane(JTabbedPane.BOTTOM);
		jtp.addTab("物块编辑", mod_Panelx);
		jtp.addTab("帧编辑", frame_panelx);
		jtp.addTab("动画编辑", anime_panelx);
		mthread = new MyUpdateThread();
		contents.add(jtp);
		setVisible(true);
	}

	public static void main(String args[]) {
		CommonUtil.setMySkin(2);
		new IkaAnimeEditor();
	}


}
