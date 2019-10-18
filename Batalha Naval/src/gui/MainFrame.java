package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import rules.*;

public class MainFrame extends JFrame {

	final int LARG_DEFAULT = 1024;
	final int ALT_DEFAULT = 768;
	
	public MainFrame(/*CtrlRules c*/) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int sl = screenSize.width;
		int sa = screenSize.height;
		int x = sl/2-LARG_DEFAULT/2;
		int y = sa/2-ALT_DEFAULT/2;
		setBounds(x,y,LARG_DEFAULT,ALT_DEFAULT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		
		//getContentPane().add(new JP_InitialScreen());
		
		getContentPane().add(new JP_MouseBoard());
		
		
		/*JP_Board tabuleiro1 = new JP_Board();
		tabuleiro1.setBounds(8,8,500,500);
		
		JP_Board tabuleiro2 = new JP_Board();
		tabuleiro2.setBounds(516,8,500,500);
		
		getContentPane().add(tabuleiro1);
		getContentPane().add(tabuleiro2);
		*/
		
		
		
		setTitle("Batalha Naval");
	}
	
	public static void main(String[] args) {
		
		try { 
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
	    } catch(Exception ignored){}
		
		new MainFrame().setVisible(true);
		

	}

}
