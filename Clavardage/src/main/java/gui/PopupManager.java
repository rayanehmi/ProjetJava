package gui;

public class PopupManager implements Runnable {
	private String pseudo="";
	public PopupManager(String pseudo) {
		this.pseudo=pseudo;
	}
	@Override
	public void run() {
		Popup popup = new Popup(pseudo);
		popup.setAlwaysOnTop(true);
		popup.setVisible(true);
		try { Thread.sleep(500); }
		catch (Exception e) {};
		popup.dispose();
	}

}