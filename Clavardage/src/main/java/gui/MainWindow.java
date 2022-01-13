package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Font;

public class MainWindow extends JFrame {
	public String pseudoChoisi;
	public String pseudo;
	public JPanel contentPane;
	public ArrayList<String> arrayConnectes;
	public JList listeConnectes;

	/**
	 * Create the frame.
	 */
	public MainWindow(String pseudo, ArrayList<String> arrayCo) {
		this.pseudo=pseudo;
		this.arrayConnectes = arrayCo;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 69, 181, 385);
		contentPane.add(scrollPane);
		
		listeConnectes = new JList(arrayConnectes.toArray());
		scrollPane.setViewportView(listeConnectes);
		listeConnectes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeConnectes.setBorder(new LineBorder(SystemColor.windowBorder));
		
		JButton btnNewButton = new JButton("Choose");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(106, 464, 85, 21);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pseudoChoisi = "pseudo";
			}
		});
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 10, 830, 50);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea("Connected as :\n"+pseudo);
		textArea_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea_1.setEditable(false);
		textArea_1.setBackground(SystemColor.menu);
		textArea_1.setBounds(850, 10, 224, 50);
		contentPane.add(textArea_1);
		
		JButton btnNewButton_1 = new JButton("Refresh");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(11, 464, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		textArea_2.setBounds(201, 69, 840, 416);
		contentPane.add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setBounds(10, 495, 773, 76);
		contentPane.add(textArea_3);
		
		JButton btnNewButton_2 = new JButton("Envoyer");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(793, 497, 248, 34);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Partager fichier");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(793, 541, 248, 30);
		contentPane.add(btnNewButton_3);
	}
}
