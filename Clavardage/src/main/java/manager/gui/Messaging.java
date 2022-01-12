package manager.gui;

import manager.*;


import java.awt.EventQueue;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollBar;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import javax.swing.Box;
import java.awt.TextArea;
import javax.swing.JScrollPane;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseWheelEvent;
import java.awt.GridBagLayout;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.Font;

public class Messaging {

	public boolean flag= false;
	public String messageReceived = "Initialization";
	public String messageSent = "";
	public JFrame frame;
	private JTextField textField;
	private JButton btnNewButton_1;
	JTextArea textArea = new JTextArea();
	private final Action action = new SwingAction();
	private JScrollPane scrollPane;
	public String name = "?";


	/**
	 * Create the application.
	 */
	public Messaging() {
		
		initLookAndFeel();
		//Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void setMessage(String msg) {
		this.messageReceived=msg;
		updateTextArea(msg);
	}
	
	public String getMessage() {
		this.flag=false;
		System.out.print("");
		return this.messageSent;
	}
	

	public boolean getFlag() {
		return this.flag;
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1067, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setBounds(10, 501, 912, 52);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textArea.setFont(new Font("Arial", Font.PLAIN, 16));
		
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				      System.out.println("Enter was pressed");
				   }
			}
		});
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setAction(action);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				messageSent=textField.getText();
				flag=true;
				textField.setText("");
				//System.out.println("debugM2");
				System.out.println(messageSent);
				//if (flag) System.out.println("flag actif");
			}
		});
		btnNewButton.setBounds(932, 532, 111, 21);
		frame.getContentPane().add(btnNewButton);
		
		
		
		btnNewButton_1 = new JButton("Files");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(932, 501, 111, 21);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Discussion with "+name);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 10, 401, 16);
		frame.getContentPane().add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 1033, 455);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(textArea);

	}
	
	private void updateTextArea(String msg) {
		this.textArea.append("\t\t"+msg+"\n");
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
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Send");
			putValue(SHORT_DESCRIPTION, "SendsMessage");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
