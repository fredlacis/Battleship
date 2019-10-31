package gui.shipSelection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import gui.JP_Utilities;
import main.K;

@SuppressWarnings("serial")
public class JF_ShipSelection extends JFrame{

	public JF_ShipSelection() {
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

		getContentPane().add(new JP_ShipOptions());
		getContentPane().add(new JP_ShipPlacement());
		getContentPane().add(new JP_Utilities());

	}

}