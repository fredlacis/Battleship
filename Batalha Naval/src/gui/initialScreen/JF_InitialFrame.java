package gui.initialScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import rules.*;

public class JF_InitialFrame extends JFrame {

	final int LARG_DEFAULT = 1024;
	final int ALT_DEFAULT = 768;
	
	private static JF_InitialFrame instance;
	
	public JF_InitialFrame(/*CtrlRules c*/) {
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
		getContentPane().setBackground(new Color(250, 250, 250));
		
		getContentPane().add(new JP_InitialScreen(this));
		
		setTitle("Batalha Naval");
	}
	
	public void setNewPanel(JPanel p) {
		 getContentPane().removeAll();
         validate();
         setContentPane(p);
	}

}
