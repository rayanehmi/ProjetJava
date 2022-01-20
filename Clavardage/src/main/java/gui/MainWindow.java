package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.JTabbedPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public String pseudoChoisi="";
	public String pseudo;
	public JPanel contentPane;
	public ArrayList<String> arrayConnectes;
	public JList<String> listeConnectes;
	public JTextArea feedback;
	public String messageToSend="";
	public boolean refreshFlag = false;
	public JTabbedPane tabbedPane;
	public ArrayList<JTextPane> tabList = new ArrayList<JTextPane>();
	public ArrayList<String> openedTabNames = new ArrayList<String>();
	public boolean exitFlag = false;
	/**
	 * Create the frame.
	 */
	
	public MainWindow(String pseudo, ArrayList<String> arrayCo) {
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
            	exitFlag=true;
            }
        });
		
		this.pseudo=pseudo;
		this.arrayConnectes = arrayCo;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		listeConnectes.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JButton btnNewButton = new JButton("Choose");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(106, 464, 85, 21);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pseudoChoisi = (String) listeConnectes.getSelectedValue();
				feedback.setText("Connexion a "+pseudoChoisi+"...");
			}
		});
		
		feedback = new JTextArea(" Choisissez un interlocuteur.");
		feedback.setBackground(new Color(245, 245, 245));
		feedback.setForeground(Color.BLACK);
		feedback.setFont(new Font("Tahoma", Font.PLAIN, 24));
		feedback.setEditable(false);
		feedback.setBounds(10, 10, 830, 39);
		contentPane.add(feedback);
		
		JTextArea textArea_1 = new JTextArea("Connected as :\n"+pseudo);
		textArea_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea_1.setEditable(false);
		textArea_1.setBackground(UIManager.getColor("Button.background"));
		textArea_1.setBounds(850, 10, 224, 51);
		contentPane.add(textArea_1);
		
		JButton btnNewButton_1 = new JButton("Refresh");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshFlag=true;
				feedback.setText("Rafraichissement des pseudos ...");
				
			}
		});
		btnNewButton_1.setBounds(11, 464, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JTextArea textAreaMessage = new JTextArea();
		textAreaMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					messageToSend = textAreaMessage.getText();
					System.out.println("Changement du msg : "+messageToSend);
					textAreaMessage.setText("");
				}
			}
		});
		textAreaMessage.setBounds(10, 495, 823, 76);
		contentPane.add(textAreaMessage);
		
		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnEnvoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if(messageToSend=="") {
				
					messageToSend = textAreaMessage.getText();
					System.out.println("Changement du msg : "+messageToSend);
					textAreaMessage.setText("");
				//}
			}
		});
		btnEnvoyer.setBounds(843, 497, 231, 34);
		contentPane.add(btnEnvoyer);
		
		JButton btnNewButton_3 = new JButton("Partager fichier");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(843, 541, 231, 30);
		contentPane.add(btnNewButton_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(201, 69, 875, 415);
		contentPane.add(scrollPane_1);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		scrollPane_1.setViewportView(tabbedPane);
		
		
		
	}
	
	/**
	 * Ajoute une tab dans la gui contenant un JTextPane.
	 * Rajoute le pseudo dans la liste openedTabNames.
	 * Rajoute le JTextPane dans la liste tabList.
	 */
	public void addTabToPane(String pseudo)
    {
		JTextPane textPane = new JTextPane();
		textPane.setBounds(201, 70, 875, 415);
		textPane.setEditable(false);
        tabbedPane.addTab(pseudo, textPane);
        tabList.add(textPane);
        openedTabNames.add(pseudo);
    }
	
	/**
	 * Ajoute au panel principal le message "msg" avec la couleur "c".
	 * Cree la tab si elle n'existe pas.
	 * @param msg
	 * @param c
	 */
	public void appendToTabbedPane(String pseudo, String msg, Color c)
    {
		
		int index = openedTabNames.indexOf(pseudo);
		if (index==-1) {
			addTabToPane(pseudo);
			index = openedTabNames.indexOf(pseudo);
		}
		JTextPane textPane = tabList.get(index);
		
		
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Tahoma");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		textPane.setEditable(true);
		int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(msg);
        textPane.setEditable(false);
        
    }
	
	/**
	 * Ajoute au panel principal le message "msg" avec la couleur "c".
	 * @param msg
	 * @param c
	 */
	public void appendToPane(JTextPane mainTextPane, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Tahoma");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = mainTextPane.getDocument().getLength();
        mainTextPane.setCaretPosition(len);
        mainTextPane.setCharacterAttributes(aset, false);
        mainTextPane.replaceSelection(msg);
    }
	
	/**
	 * Efface le contenu du panel principal.
	 */
	public void erasePane(JTextPane textPane)
    {
        textPane.setText("");
    }
}
