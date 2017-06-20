package com.ikags.animeeditor2.util;

import java.awt.image.BufferedImage;

public class IKAImageUtil {
	public static BufferedImage create_mod_image(BufferedImage bimage, int x, int y, int w, int h) {
		BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		tag.getGraphics().drawImage(bimage, -x, -y, bimage.getWidth(), bimage.getHeight(), null);
		return tag;
	}
}
