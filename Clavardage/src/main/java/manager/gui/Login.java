package manager.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				      System.out.println("Enter was pressed");
				   }
			}
		});
		textField.setBounds(136, 147, 322, 53);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Choisir ce pseudo");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(170, 210, 266, 35);
		contentPane.add(btnNewButton);
		
		JTextArea txtrnn = new JTextArea("\n\n        Merci d'entrer votre pseudo ci-dessous");
		txtrnn.setBackground(SystemColor.menu);
		txtrnn.setLineWrap(true);
		txtrnn.setFont(new Font("Arial", Font.PLAIN, 24));
		txtrnn.setBounds(30, 27, 529, 110);
		contentPane.add(txtrnn);
		
		JTextArea textAreaFeedback = new JTextArea();
		textAreaFeedback.setForeground(Color.RED);
		textAreaFeedback.setFont(new Font("Arial", Font.PLAIN, 22));
		textAreaFeedback.setEditable(false);
		textAreaFeedback.setBackground(SystemColor.menu);
		textAreaFeedback.setBounds(86, 274, 421, 59);
		contentPane.add(textAreaFeedback);
	}
}
