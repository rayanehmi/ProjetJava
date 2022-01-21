package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.swt.graphics.Image;

import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Color;

public class Popup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String pseudo;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public Popup(String pseudo) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 386, 125);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JTextPane txtpnSestDeconnecte = new JTextPane();
		txtpnSestDeconnecte.setForeground(new Color(220, 20, 60));
		txtpnSestDeconnecte.setBounds(10, 11, 357, 77);
		txtpnSestDeconnecte.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtpnSestDeconnecte.setText(pseudo+" s'est deconnecte.");
		contentPane.add(txtpnSestDeconnecte);
		
	}
}
