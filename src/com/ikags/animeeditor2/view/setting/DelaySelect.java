package com.ikags.animeeditor2.view.setting;
import java.awt.Adjustable;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import com.ikags.animeeditor2.SDef;

/**
 * <p>Title:ika 动画编辑器</p>
 * <p>Description: 编辑图片和帧，形成动画，导出后给手机调用</p>
 * <p>Copyright: airzhangfish Copyright (c) 2007</p>
 * <p>blog: http://airzhangfish.spaces.live.com</p>
 * <p>Company: Comicfishing</p>
 * <p>author airzhangfish</p>
 * <p>version 0.03a standard</p>
 * <p>last updata 2007-8-23</p>
 * 单帧速度设定面板
 */

public class DelaySelect
    extends JFrame
    implements AdjustmentListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JLabel redLabel;
  private JScrollBar red;
  public DelaySelect() {
    setTitle("单帧速度设定");
    setSize(300, 100);
    this.setResizable(false); //窗体不能改变大小
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this.windowClosed(e);
      }
    });

    Container contentPane = getContentPane();

    JPanel p = new JPanel();
    p.setLayout(new GridLayout(3, 2));

    p.add(redLabel = new JLabel("帧速度设定 " + SDef.SYSTEM_DELAY));
    p.add(red = new JScrollBar(Adjustable.HORIZONTAL, SDef.SYSTEM_DELAY, 0, 0, 500));
    red.setBlockIncrement(16);
    red.addAdjustmentListener(this);
    contentPane.add(p, "South");
  }

  public void adjustmentValueChanged(AdjustmentEvent evt) {
    redLabel.setText("帧速度设定 " + red.getValue());
    SDef.SYSTEM_DELAY = red.getValue();
  }
}
