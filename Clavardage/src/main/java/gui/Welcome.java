package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.Color;

public class Welcome extends JFrame {
	
	public String pseudoChoisi;
	private JPanel contentPane;
	private JTextField textField;
	public JTextArea feedbackText;

	
	public Welcome() {
		
		initLookAndFeel();
		//Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
		initialize();
	}
	
	private static void initLookAndFeel() {
        
        // Swing allows you to specify which look and feel your program uses--Java,
        // GTK+, Windows, and so on as shown below.
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
       
            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (Exception e) {
				e.printStackTrace();
			}
        }
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 705, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Se connecter");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pseudo = textField.getText();
				if (pseudo != null) {
					pseudoChoisi=pseudo;
				}
			}
		});
		btnNewButton.setBounds(242, 250, 194, 43);
		contentPane.add(btnNewButton);
		
		JTextArea txtrBienvenueMerciDentrer = new JTextArea();
		txtrBienvenueMerciDentrer.setEditable(false);
		txtrBienvenueMerciDentrer.setWrapStyleWord(true);
		txtrBienvenueMerciDentrer.setBackground(UIManager.getColor("Button.background"));
		txtrBienvenueMerciDentrer.setText("Bienvenue\r\nMerci d'entrer votre pseudonyme ci dessous");
		txtrBienvenueMerciDentrer.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtrBienvenueMerciDentrer.setBounds(10, 92, 671, 95);
		contentPane.add(txtrBienvenueMerciDentrer);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBounds(93, 197, 505, 43);
		contentPane.add(textField);
		textField.setColumns(10);
		
		feedbackText = new JTextArea();
		feedbackText.setForeground(new Color(255, 0, 0)); //Red
		feedbackText.setFont(new Font("Tahoma", Font.PLAIN, 24));
		feedbackText.setBackground(UIManager.getColor("Button.background"));
		feedbackText.setBounds(120, 303, 455, 62);
		contentPane.add(feedbackText);
		
	}
	
	
}
