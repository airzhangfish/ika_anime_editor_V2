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
 * <p>Title:ika �����༭��</p>
 * <p>Description: �༭ͼƬ��֡���γɶ�������������ֻ�����</p>
 * <p>Copyright: airzhangfish Copyright (c) 2007</p>
 * <p>blog: http://airzhangfish.spaces.live.com</p>
 * <p>Company: Comicfishing</p>
 * <p>author airzhangfish</p>
 * <p>version 0.03a standard</p>
 * <p>last updata 2007-8-23</p>
 * ��֡�ٶ��趨���
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
    setTitle("��֡�ٶ��趨");
    setSize(300, 100);
    this.setResizable(false); //���岻�ܸı��С
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this.windowClosed(e);
      }
    });

    Container contentPane = getContentPane();

    JPanel p = new JPanel();
    p.setLayout(new GridLayout(3, 2));

    p.add(redLabel = new JLabel("֡�ٶ��趨 " + SDef.SYSTEM_DELAY));
    p.add(red = new JScrollBar(Adjustable.HORIZONTAL, SDef.SYSTEM_DELAY, 0, 0, 500));
    red.setBlockIncrement(16);
    red.addAdjustmentListener(this);
    contentPane.add(p, "South");
  }

  public void adjustmentValueChanged(AdjustmentEvent evt) {
    redLabel.setText("֡�ٶ��趨 " + red.getValue());
    SDef.SYSTEM_DELAY = red.getValue();
  }
}
