package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.JTextField;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class YoutubeSearch extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private JTextField input;
	private JLabel lblError;

	/**
	 * Create the youtube search dialog.
	 */
	public YoutubeSearch() {
		setBounds(500, 300, 545, 369);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		input = new JTextField();
		input.setFont(new Font("Tahoma", Font.PLAIN, 15));
		input.setBackground(SystemColor.menu);
		input.setBounds(118, 204, 288, 32);
		contentPanel.add(input);
		input.setColumns(10);
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
				}
			}
		});

		JButton btnSearch = new JButton("Search");
		btnSearch.setContentAreaFilled(false);
		btnSearch.setOpaque(true);
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSearch.setBackground(new Color(51, 51, 51));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setBounds(422, 204, 79, 32);
		contentPanel.add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});

		JButton btnSkip = new JButton("Skip");
		btnSkip.setContentAreaFilled(false);
		btnSkip.setOpaque(true);
		btnSkip.setForeground(new Color(0, 0, 0));
		btnSkip.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSkip.setBackground(new Color(255, 255, 255));
		btnSkip.setBounds(236, 288, 88, 36);
		contentPanel.add(btnSkip);
		btnSkip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skip();
			}
		});
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblError.setBounds(118, 184, 96, 14);
		contentPanel.add(lblError);
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(YoutubeSearch.class.getResource("/images/youtube.png")));
		lblNewLabel.setBounds(0, 0, 545, 369);
		contentPanel.add(lblNewLabel);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setAlwaysOnTop(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	protected void search() {
		if (input.getText().trim().equals(""))
			lblError.setText("Invalid input.");
		else {
			lblError.setText("");
			String url = "https://www.youtube.com/results?search_query=";
			// remove extra white spaces
			String userInput = input.getText().trim().replaceAll(" +", " ");
			// replace remaining white spaces with '+'
			userInput = userInput.replaceAll(" ", "+");
			// open web browser
			try {
				Desktop.getDesktop().browse(new URL(url + userInput).toURI());
				skip();
			} catch (Exception k) {
				k.printStackTrace();
				lblError.setText("Search failed.");
			}
		}
	}

	/**
	 * Go to the main menu
	 */
	private void skip() {
		Start.frame.setVisible(false);
		Start.menu.setVisible(true);
		setVisible(false);
		dispose();
	}
}
