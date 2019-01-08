package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import controller.SysData;


// black panel for to darken the game

@SuppressWarnings("serial")
public class BlackPanel extends JFrame {

	/**
	 * Create the panel.
	 */
	public BlackPanel() {
		setResizable(false);
		SysData.getInstance().readJson();
		setPreferredSize(new Dimension(1019, 688));
		setMaximumSize(new Dimension(1019, 688));
		setMinimumSize(new Dimension(1019, 688));
		setLocationRelativeTo(null);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		getContentPane().setBackground(new Color(0, 0, 0, 90));
//		setBounds(173, 15, 1017, 688);
		setBackground(new Color(0, 0, 0, 90));
		setVisible(false);
		setAlwaysOnTop(true);
		setEnabled(false);
	}

}
