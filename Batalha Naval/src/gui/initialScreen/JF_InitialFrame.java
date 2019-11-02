package gui.initialScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.K;
import rules.*;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JF_InitialFrame extends JFrame{

	public JF_InitialFrame(/*CtrlRules c*/) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int sl = screenSize.width;
		int sa = screenSize.height;
		int x = sl/2-K.LARG_DEFAULT/2;
		int y = sa/2-K.ALT_DEFAULT/2;
		setBounds(x,y,K.LARG_DEFAULT,K.ALT_DEFAULT);
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
