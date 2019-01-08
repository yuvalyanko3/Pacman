package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.SysData;
import model.Question;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class ManageQues extends JPanel {
	private JTextField question;
	private JTextField answer1;
	private JTextField answer2;
	private JTextField answer4;
	private JTextField answer3;
	private JComboBox<String> correct;
	private JComboBox<String> level;
	public JList<String> questionsList = new JList<>();

	/**
	 * Create the panel.
	 */
	public ManageQues() {
		this.setBounds(150, 0, 1019, 688);
		this.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 1017, 688);
		add(panel);

		JButton btnBackButton = new JButton("Back");
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

		question = new JTextField();
		question.setBackground(SystemColor.menu);
		question.setFont(new Font("Tahoma", Font.PLAIN, 12));
		question.setBounds(49, 178, 448, 39);
		panel.add(question);
		question.setColumns(10);

		JButton btnSave = new JButton("Save Question");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSave.setBackground(Color.WHITE);
		btnSave.setContentAreaFilled(false);
		btnSave.setOpaque(true);
		btnSave.setBounds(165, 570, 224, 48);
		panel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean flag = true;
				if ((!question.getText().equals("")) && correct.getSelectedIndex() != -1
						&& level.getSelectedIndex() != -1) {
					ArrayList<String> a = new ArrayList<>();
					if (!answer1.getText().equals(""))
						a.add(answer1.getText());
					if (!answer2.getText().equals(""))
						a.add(answer2.getText());
					if (!answer3.getText().equals(""))
						a.add(answer3.getText());
					if (!answer4.getText().equals(""))
						a.add(answer4.getText());
					for (int i = 0; i < a.size(); i++) {
						for (int j = 1; j < a.size(); j++) {
							if (j != i && (!a.get(i).equals("") || !a.get(j).equals("")) && a.get(i).equals(a.get(j)))
								flag = false;
						}
					}
					if (flag == false) {
						JOptionPane.showMessageDialog(null, "There are two identical answers", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						String c = (String) correct.getSelectedItem();
						String l = (String) level.getSelectedItem();
						if (SysData.getInstance().addQuestion(question.getText(), a, c, l)) {
							JOptionPane.showMessageDialog(null, "Question added successfully");
							refreshQuesList();
						} else
							JOptionPane.showMessageDialog(null, "Question was not added", "Error",
									JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "There are empty fields", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnSave.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent e) {
				btnSave.setBackground(Color.WHITE);
			}
		});

		JButton btnNew = new JButton("New Question");
		btnNew.setContentAreaFilled(false);
		btnNew.setOpaque(true);
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNew.setBackground(Color.WHITE);
		btnNew.setBounds(645, 570, 224, 48);
		panel.add(btnNew);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				question.setText("");
				answer1.setText("");
				answer2.setText("");
				answer3.setText("");
				answer4.setText("");
				correct.setSelectedIndex(-1);
				level.setSelectedIndex(-1);
			}
		});
		btnNew.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnNew.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent e) {
				btnNew.setBackground(Color.WHITE);
			}
		});

		JButton btnDelete = new JButton("Delete Question");
		btnDelete.setContentAreaFilled(false);
		btnDelete.setOpaque(true);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDelete.setBackground(SystemColor.textHighlightText);
		btnDelete.setBounds(645, 505, 224, 48);
		panel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!questionsList.getSelectedValue().equals("")) {
					if (SysData.getInstance().deleteQuestion(questionsList.getSelectedValue())) {
						JOptionPane.showMessageDialog(null, "Question deleted successfully");
						refreshQuesList();
					} else
						JOptionPane.showMessageDialog(null, "Question was not deleted", "Error",
								JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnDelete.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent e) {
				btnDelete.setBackground(Color.WHITE);
			}
		});

		answer1 = new JTextField();
		answer1.setBackground(SystemColor.menu);
		answer1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		answer1.setColumns(10);
		answer1.setBounds(88, 233, 409, 39);
		panel.add(answer1);

		answer2 = new JTextField();
		answer2.setBackground(SystemColor.control);
		answer2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		answer2.setColumns(10);
		answer2.setBounds(88, 290, 409, 39);
		panel.add(answer2);

		answer4 = new JTextField();
		answer4.setBackground(SystemColor.menu);
		answer4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		answer4.setColumns(10);
		answer4.setBounds(88, 404, 409, 39);
		panel.add(answer4);

		answer3 = new JTextField();
		answer3.setBackground(SystemColor.menu);
		answer3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		answer3.setColumns(10);
		answer3.setBounds(88, 347, 409, 39);
		panel.add(answer3);

		correct = new JComboBox<String>();
		correct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		correct.setBounds(314, 467, 93, 28);
		panel.add(correct);
		for (int i = 1; i < 5; i++)
			correct.addItem("" + i);

		level = new JComboBox<String>();
		level.setFont(new Font("Tahoma", Font.PLAIN, 14));
		level.setBounds(314, 509, 93, 28);
		panel.add(level);
		for (int i = 1; i < 4; i++)
			level.addItem("" + i);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(527, 148, 443, 295);
		panel.add(scrollPane);
		questionsList = new JList<>(new DefaultListModel<String>());
		questionsList.setForeground(SystemColor.controlText);
		questionsList.setBackground(SystemColor.menu);
		questionsList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(questionsList);
		questionsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (questionsList.getSelectedIndex() != -1) {
					Question q = SysData.getInstance().getQuMap().get(questionsList.getSelectedValue());
					if (q != null) {
						question.setText(q.getQuestionString());
						ArrayList<String> a = q.getAnswer().getAnswers();
						int size = 0;
						if (!a.equals(null))
							answer1.setText(size >= a.size() ? "" : a.get(size));
						size++;
						answer2.setText(size >= a.size() ? "" : a.get(size));
						size++;
						answer3.setText(size >= a.size() ? "" : a.get(size));
						size++;
						answer4.setText(size >= a.size() ? "" : a.get(size));
						size++;
						correct.setSelectedItem(q.getCorrectAnswer());
						level.setSelectedItem(q.getLevel());
					}
				}
			}
		});

		JLabel label = new JLabel();
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setIcon(new ImageIcon("assets/images/manageQuestins.png"));
		label.setBounds(0, 0, 1017, 682);
		panel.add(label);

		refreshQuesList();

	}

	/**
	 * Refresh the questions list after updates
	 */
	public void refreshQuesList() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		if (SysData.getInstance().getQuestions() != null) {
			for (Question q : SysData.getInstance().getQuestions()) {
				if (q != null) {
					listModel.addElement(q.getQuestionString());
					questionsList.setModel(listModel);
					questionsList.setSelectedIndex(0);
				}
			}
		}
	}
}
