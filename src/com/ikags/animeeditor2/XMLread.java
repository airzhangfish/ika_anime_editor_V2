package com.ikags.animeeditor2;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ikags.animeeditor2.info.AnimeInfo;
import com.ikags.animeeditor2.info.BigImageInfo;
import com.ikags.animeeditor2.info.FrameInfo;
import com.ikags.animeeditor2.info.ImageModInfo;
import com.ikags.animeeditor2.info.ModInfo;
import com.ikags.animeeditor2.info.TotalInfo;
import com.ikags.animeeditor2.util.IKAImageUtil;
import com.ikags.animeeditor2.view.FramePanel;

public class XMLread {

	public XMLread(String namepath) {

		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			InputStream is = new FileInputStream(namepath);
			Document doc = dombuilder.parse(is);
			Element root = doc.getDocumentElement();
			NodeList ikaanime = root.getChildNodes();
			SDef.mTotalInfo = new TotalInfo();
			if (ikaanime != null) {
				SDef.mod_size = 1;
				for (int kk = 0; kk < ikaanime.getLength(); kk++) {
					Node book = ikaanime.item(kk);

					if (book.getNodeType() == Node.ELEMENT_NODE) {
						if (book.getNodeName().equals("bigimage")) {
							BigImageInfo bii = new BigImageInfo();

							bii.mPath = book.getAttributes().getNamedItem("path").getNodeValue();
							System.out.println("bii.mPath="+bii.mPath);
							File pngfile = new File(bii.mPath);
							bii.mBigImage = ImageIO.read(pngfile);
							bii.mStaticBigImage = bii.mBigImage;

							SDef.mTotalInfo.addBigImageInfo(bii);

						} else if (book.getNodeName().equals("imagemod")) {
							// 物块
							ImageModInfo imi = new ImageModInfo();

							imi.mX = Integer.parseInt(book.getAttributes().getNamedItem("x").getNodeValue());
							imi.mY = Integer.parseInt(book.getAttributes().getNamedItem("y").getNodeValue());
							imi.mWidth = Integer.parseInt(book.getAttributes().getNamedItem("width").getNodeValue());
							imi.mHeight = Integer.parseInt(book.getAttributes().getNamedItem("height").getNodeValue());
							imi.mUsed = Integer.parseInt(book.getAttributes().getNamedItem("use").getNodeValue());
							imi.mBigImageID = Integer.parseInt(book.getAttributes().getNamedItem("image").getNodeValue());
							try{
							imi.mInfo = book.getAttributes().getNamedItem("info").getNodeValue();
							}catch(Exception ex){
							}
							// imi.mInfo = book.getAttributes().getNamedItem("info").getNodeValue();
							SDef.mTotalInfo.addImageModInfo(imi);

						} else if (book.getNodeName().equals("frame")) {

							FrameInfo fi = new FrameInfo();

							// 帧
							fi.mInfo = book.getAttributes().getNamedItem("info").getNodeValue();
							// 使用中的物块

							// 统计frame_mod_number数目
							for (Node node = book.getFirstChild(); node != null; node = node.getNextSibling()) {
								if (node.getNodeType() == Node.ELEMENT_NODE) {
									if (node.getNodeName().equals("mod")) {
										ModInfo mod = new ModInfo();
										mod.mImageModID = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
										mod.mX = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
										mod.mY = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
										mod.mRotateType = Integer.parseInt(node.getAttributes().getNamedItem("rtype").getNodeValue());
										mod.mRotate = Integer.parseInt(node.getAttributes().getNamedItem("rotate").getNodeValue());
										mod.mScale = Float.parseFloat(node.getAttributes().getNamedItem("scale").getNodeValue());
										mod.mInfo = node.getAttributes().getNamedItem("info").getNodeValue();
										fi.addModInfo(mod);
									}
								}
							}
							SDef.mTotalInfo.addFrameInfo(fi);
						} else if (book.getNodeName().equals("anime")) {

							AnimeInfo ani = new AnimeInfo();
							ani.mInfo = book.getAttributes().getNamedItem("info").getNodeValue();
							// 使用中的物块

							// 统计frame_mod_number数目
							int anime_frame_number = 0;
							for (Node node = book.getFirstChild(); node != null; node = node.getNextSibling()) {
								if (node.getNodeType() == Node.ELEMENT_NODE) {
									if (node.getNodeName().equals("frame")) {
										Integer myint = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
										ani.addFrameInfo(myint);
									}
								}
							}
							
							SDef.mTotalInfo.addAnimeInfo(ani);
							
							
						}
					}
				}

				// 统计名字(图片)
				// SDef.mod_Image_name = new String[ImageNumber];
				SDef.image_listModel.clear();
				for (int i = 0; i < SDef.mTotalInfo.mBigImagelist.size(); i++) {
					BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.elementAt(i);
					SDef.image_listModel.addElement(bii.getName());
				}

				SDef.mod_listModel.clear();
				for (int i = 0; i < SDef.mTotalInfo.mImageModlist.size(); i++) {
					ImageModInfo imi = SDef.mTotalInfo.mImageModlist.elementAt(i);
					BigImageInfo bii = SDef.mTotalInfo.mBigImagelist.get(imi.mBigImageID);
					imi.mImage = IKAImageUtil.create_mod_image(bii.mBigImage, imi.mX, imi.mY, imi.mWidth, imi.mHeight);
					imi.mStaticImage=imi.mImage;
					SDef.mod_listModel.addElement(imi.getName());
				}

				SDef.frame_listModel.clear();
				for (int i = 0; i < SDef.mTotalInfo.mFramelist.size(); i++) {
					FrameInfo fi = SDef.mTotalInfo.mFramelist.elementAt(i);
					SDef.frame_listModel.addElement(fi.getName());
				}

				FramePanel.up_frame_list();

				SDef.anime_listModel.clear();
				for (int i = 0; i < SDef.mTotalInfo.mAnimelist.size(); i++) {
					AnimeInfo anif = SDef.mTotalInfo.mAnimelist.elementAt(i);
					SDef.anime_listModel.addElement(anif.getName());
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "数组出错 code="+e.getMessage(), "出错", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
