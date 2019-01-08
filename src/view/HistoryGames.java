package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.SysData;
import model.GameHistory;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class HistoryGames extends JPanel {
	private JScrollPane scrollPane;
	private JTable table;
	private Top10 top10;
	private JButton button;
	private JButton btnBackButton;
	private JLabel blackScreen = new JLabel();

	/**
	 * Create the panel.
	 */
	public HistoryGames() {
		this.setBounds(150, 0, 1019, 688);
		this.setLayout(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 1017, 688);
		add(panel);

		blackScreen = new JLabel("");
		blackScreen.setBounds(0, 0, 1017, 688);
		blackScreen.setIcon(new ImageIcon("assets/images/blackScreen.png"));
		blackScreen.setBackground(Color.BLACK);
		blackScreen.setVisible(false);
		panel.add(blackScreen);

		btnBackButton = new JButton("Back");
		btnBackButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnBackButton.setContentAreaFilled(false);
		btnBackButton.setOpaque(true);
		btnBackButton.setBackground(Color.BLACK);
		btnBackButton.setForeground(Color.WHITE);
		btnBackButton.setBounds(936, 11, 81, 23);
		panel.add(btnBackButton);
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.menu.setContentPane(Start.menu.contentPane);
				Start.menu.contentPane.setVisible(true);
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(102, 125, 815, 409);
		panel.add(scrollPane);

		// create the table with history games
		String col[] = { "Date", "Game Number", "Player Name", "Final Score", "Questions", "Right Answers" };
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		ArrayList<GameHistory> h = SysData.getInstance().getGamesHistory();
		// sort the list by game date
		Collections.sort(h, new Comparator<GameHistory>() {
			@Override
			public int compare(GameHistory o1, GameHistory o2) {
				return (o2.getDate().compareTo(o1.getDate()));
			}
		});
		for (GameHistory gh : h) {
			if (gh != null) {
				Object[] o = new Object[] { gh.getDate(), gh.getNumber(), gh.getName(), gh.getFinalScore(),
						gh.getQuestionsDone(), gh.getRightAnswers() };
				tableModel.addRow(o);
			}
		}
		table = new JTable(tableModel);
		table.setForeground(SystemColor.controlText);
		table.setBackground(SystemColor.menu);
		table.setEnabled(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);

		// design table
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setFont(new Font(null, Font.BOLD, 16));
		table.getTableHeader().setBackground(Color.black);
		table.getTableHeader().setForeground(Color.white);

		table.setRowHeight(30);
		DefaultTableCellRenderer cRenderer = new DefaultTableCellRenderer();
		cRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(cRenderer);

		button = new JButton("TOP 10");
		button.setContentAreaFilled(false);
		button.setOpaque(true);
		button.setFont(new Font("Tahoma", Font.PLAIN, 22));
		button.setBackground(Color.WHITE);
		button.setBounds(812, 67, 105, 41);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				top10 = new Top10();
				top10.setVisible(true);
				blackScreen.setVisible(true);
				scrollPane.setVisible(false);
				button.setVisible(false);
				btnBackButton.setVisible(false);
			}
		});

		JLabel label = new JLabel();
		label.setBackground(SystemColor.menu);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setIcon(new ImageIcon("assets/images/history.png"));
		label.setBounds(0, -13, 1017, 695);
		panel.add(label);
	}

	/**
	 * TOP 10 panel.
	 */
	public class Top10 extends JFrame {
		private final JPanel contentPanel = new JPanel();
		private JScrollPane scrollPane2;

		/**
		 * Create the panel.
		 */
		public Top10() {
			setBounds(800, 800, 545, 369);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			setAlwaysOnTop(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

			JPanel panel2 = new JPanel();
			panel2.setBounds(0, 0, 545, 369);
			panel2.setBackground(Color.BLACK);
			add(panel2);
			panel2.setLayout(null);

			scrollPane2 = new JScrollPane();
			scrollPane2.setEnabled(false);
			scrollPane2.setBounds(154, 46, 353, 288);
			panel2.add(scrollPane2);

			String col[] = { "Player Name", "Final Score", "Right Answers" };
			DefaultTableModel tableModel = new DefaultTableModel(col, 0);
			ArrayList<GameHistory> h = SysData.getInstance().getTop10();
			for (GameHistory gh : h) {
				if (gh != null) {
					Object[] o = new Object[] { gh.getName(), gh.getFinalScore(),
							gh.getRightAnswers() + "/" + gh.getQuestionsDone() };
					tableModel.addRow(o);
				}
			}
			JTable table = new JTable(tableModel);
			// design table
			table.setForeground(SystemColor.controlText);
			table.setBackground(SystemColor.menu);
			table.setEnabled(false);
			table.setFont(new Font("Tahoma", Font.PLAIN, 14));
			table.setRowHeight(25);
			DefaultTableCellRenderer cRenderer = new DefaultTableCellRenderer();
			cRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			for (int i = 0; i < table.getColumnCount(); i++)
				table.getColumnModel().getColumn(i).setCellRenderer(cRenderer);
			table.getTableHeader().setOpaque(false);
			table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));
			table.getTableHeader().setBackground(Color.black);
			table.getTableHeader().setForeground(Color.white);
			scrollPane2.setViewportView(table);

			JButton btnX = new JButton("Close");
			btnX.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					blackScreen.setVisible(false);
					scrollPane.setVisible(true);
					button.setVisible(true);
					btnBackButton.setVisible(true);
				}
			});
			btnX.setOpaque(true);
			btnX.setForeground(Color.WHITE);
			btnX.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnX.setContentAreaFilled(false);
			btnX.setBackground(Color.BLACK);
			btnX.setBounds(476, 13, 71, 16);
			panel2.add(btnX);

			JLabel lblNewLabel = new JLabel("New label");
			lblNewLabel.setIcon(new ImageIcon("assets/images/top10.png"));
			lblNewLabel.setBounds(0, 0, 545, 369);
			panel2.add(lblNewLabel);

		}
	}
}
