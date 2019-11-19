package main;
import javax.swing.UIManager;

import gui.initialScreen.JF_InitialFrame;
import gui.victory.JF_Victory;

public class Launcher {

	public static void main(String[] args) {
		
		try { 
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
	    } catch(Exception ignored){}
		
//		JF_InitialFrame mainFrame = JF_InitialFrame.getInitialFrame();
		JF_Victory mainFrame = JF_Victory.getVictoryFrame("Fred", "David");
		mainFrame.setVisible(true);

	}

}
