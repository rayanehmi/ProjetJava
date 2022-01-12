package manager.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Choosing extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Choosing frame = new Choosing();
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
	public Choosing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 79, 866, 434);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(SystemColor.menu);
		
		JTextPane txtpnChoisissezVotreInterlocuteur = new JTextPane();
		txtpnChoisissezVotreInterlocuteur.setBackground(SystemColor.menu);
		txtpnChoisissezVotreInterlocuteur.setForeground(Color.DARK_GRAY);
		txtpnChoisissezVotreInterlocuteur.setText("Choisissez votre interlocuteur :");
		txtpnChoisissezVotreInterlocuteur.setFont(new Font("Tahoma", Font.PLAIN, 38));
		txtpnChoisissezVotreInterlocuteur.setEditable(false);
		txtpnChoisissezVotreInterlocuteur.setBounds(10, 10, 866, 62);
		contentPane.add(txtpnChoisissezVotreInterlocuteur);
		
		JButton btnNewButton = new JButton("Demarrer conversation");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(302, 525, 282, 28);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Rafraichir liste");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(711, 529, 165, 24);
		contentPane.add(btnNewButton_1);
	}
}
